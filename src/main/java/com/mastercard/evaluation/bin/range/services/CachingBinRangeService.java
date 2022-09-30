package com.mastercard.evaluation.bin.range.services;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.Resources;
import com.mastercard.evaluation.bin.range.events.EventManager;
import com.mastercard.evaluation.bin.range.models.BinRange;
import com.mastercard.evaluation.bin.range.models.BinRangeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class CachingBinRangeService implements BinRangeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CachingBinRangeService.class);

    private static final int ONE_HOUR_IN_MILLIS = 3600000;
    private static final String BIN_TABLE_RESOURCE_FILE_NAME = "bin-range-info-data.json";

    private final Lock lock = new ReentrantLock();
    private final ObjectMapper objectMapper;
    private final EventManager eventManager;

    private HashMap<UUID, BinRangeInfo> binRangeInfoCache = new HashMap<>();
    private NavigableMap<BinRange, UUID> binRangeInfoByBinRangeIndex = new TreeMap<>();

    @Autowired
    public CachingBinRangeService(ObjectMapper objectMapper, EventManager eventManager) {
        Preconditions.checkNotNull(objectMapper, "ObjectMapper cannot be null");
        Preconditions.checkNotNull(eventManager, "EventManager cannot be null");

        this.objectMapper = objectMapper;
        this.eventManager = eventManager;
    }

    @PostConstruct
    @Scheduled(fixedRate = ONE_HOUR_IN_MILLIS)
    @SuppressWarnings("unchecked")
    public void refreshCache() {
        LOGGER.info("Refreshing cache");

        try {
            URL url = Resources.getResource(BIN_TABLE_RESOURCE_FILE_NAME);
            JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, BinRangeInfo.class);
            List<BinRangeInfo> binTableEntries = objectMapper.readValue(Resources.toString(url, Charsets.UTF_8), type);

            populateCache(binTableEntries);
        } catch (IOException e) {
            LOGGER.error("Failed to read bin range entries from file={}", BIN_TABLE_RESOURCE_FILE_NAME, e);
        }
    }

    @Override
    public Optional<BinRangeInfo> findBinRangeInfoByPan(String pan) {
        lock.lock();

        try {
            Optional<UUID> binRangeInfo = Optional.ofNullable(binRangeInfoByBinRangeIndex.get(new BinRange(pan)));

            return binRangeInfo.isPresent() ?
                    Optional.ofNullable(binRangeInfoCache.get(binRangeInfo.get())) :
                    Optional.empty();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public BinRangeInfo addBinRangeInfo(BinRangeInfo binRangeInfo) {
        lock.lock();
        try {
            binRangeInfo.setRef(UUID.randomUUID());
            validateAndPopulateCacheAndIndices(binRangeInfo);
        } finally {
            lock.unlock();
        }
        return binRangeInfo;
    }

    private boolean isValidRange(BinRangeInfo binRangeInfo) {
        return binRangeInfoByBinRangeIndex.keySet().stream()
                .noneMatch(b -> b.getStart().equals(binRangeInfo.getStart())) ||
                binRangeInfoByBinRangeIndex.keySet().stream()
                        .noneMatch(b -> b.getEnd().equals(binRangeInfo.getEnd()));
    }

    @Override
    public boolean deleteBinRangeInfo(String id) {
        lock.lock();
        try {
            UUID uuid = UUID.fromString(id);
            if(binRangeInfoByBinRangeIndex.values().contains(uuid) || binRangeInfoCache.containsKey(uuid)) {
                binRangeInfoByBinRangeIndex.entrySet().removeIf(e -> e.getValue().equals(uuid));
                binRangeInfoCache.remove(id);
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public CachingBinRangeServiceUpdateResponse updateBinRangeInfo(String uuid, BinRangeInfo binRangeInfo) {
        lock.lock();
        try {
            BinRangeInfo oldBinRangeInfo = binRangeInfoCache.get(UUID.fromString(uuid));
            binRangeInfo.setRef(UUID.fromString(uuid));
            if(oldBinRangeInfo != null) {
                binRangeInfoCache.put(oldBinRangeInfo.getRef(), binRangeInfo);
                binRangeInfoByBinRangeIndex.put(getBinRangeFromEntry(binRangeInfo), oldBinRangeInfo.getRef());
                return new CachingBinRangeServiceUpdateResponse(binRangeInfo, true);
            }
            populateCacheAndIndices(binRangeInfo);
            return new CachingBinRangeServiceUpdateResponse(binRangeInfo, false);
        } finally {
            lock.unlock();
        }
    }

    @VisibleForTesting
    void populateCache(List<BinRangeInfo> binTableEntries) {
        lock.lock();

        try {
            binRangeInfoCache.clear();
            binRangeInfoByBinRangeIndex.clear();
            binTableEntries.forEach(this::populateCacheAndIndices);
        } finally {
            lock.unlock();
        }
    }

    private void validateAndPopulateCacheAndIndices(BinRangeInfo binRangeInfo) {
        if(isValidRange(binRangeInfo)) {
            populateCacheAndIndices(binRangeInfo);
        } else {
            throw new IllegalArgumentException("No bin ranges should share a start or end value");
        }
    }

    private void populateCacheAndIndices(BinRangeInfo entry) {
        binRangeInfoCache.put(entry.getRef(), entry);
        binRangeInfoByBinRangeIndex.put(getBinRangeFromEntry(entry), entry.getRef());
    }

    private BinRange getBinRangeFromEntry(BinRangeInfo binRangeInfo) {
        return new BinRange(binRangeInfo.getStart(), binRangeInfo.getEnd());
    }
}
