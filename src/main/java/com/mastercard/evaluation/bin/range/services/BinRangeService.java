package com.mastercard.evaluation.bin.range.services;

import com.mastercard.evaluation.bin.range.models.BinRangeInfo;

import java.util.Optional;

public interface BinRangeService {

    Optional<BinRangeInfo> findBinRangeInfoByPan(String pan);

    BinRangeInfo addBinRangeInfo(BinRangeInfo info);

    boolean deleteBinRangeInfo(String uuid);

    CachingBinRangeServiceUpdateResponse updateBinRangeInfo(String uuid, BinRangeInfo binRangeInfo);
}
