package com.mastercard.evaluation.bin.range.controllers;

import com.google.common.base.Preconditions;
import com.mastercard.evaluation.bin.range.models.BinRangeInfo;
import com.mastercard.evaluation.bin.range.services.BinRangeService;
import com.mastercard.evaluation.bin.range.services.CachingBinRangeServiceUpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/binRangeInfoSearch")
public class BinRangeInfoSearchController {

    private final BinRangeService binRangeService;

    @Autowired
    public BinRangeInfoSearchController(BinRangeService binRangeService) {
        Preconditions.checkNotNull(binRangeService, "binRangeService cannot be null");

        this.binRangeService = binRangeService;
    }

    @GetMapping(value = "/{pan}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BinRangeInfo> getBinRangeInfoByPan(@PathVariable("pan") String pan) {
        return binRangeService.findBinRangeInfoByPan(pan)
                .map(binRangeInfo -> new ResponseEntity<>(binRangeInfo, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BinRangeInfo> createBinRangeInfoByPan(@RequestBody BinRangeInfo binRangeInfo) {
        return new ResponseEntity<>(binRangeService.addBinRangeInfo(binRangeInfo), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{uuid}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BinRangeInfo> updateBinRangeInfoByPan(@PathVariable("uuid") String uuid, @RequestBody BinRangeInfo binRangeInfo) {
        CachingBinRangeServiceUpdateResponse response = binRangeService.updateBinRangeInfo(uuid, binRangeInfo);
        if(response.isUpdated()) {
            return new ResponseEntity<>(binRangeInfo, HttpStatus.OK);
        }
        return new ResponseEntity<>(binRangeInfo, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{uuid}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<String> delete(@PathVariable("uuid") String uuid) {
        boolean isDeleted = binRangeService.deleteBinRangeInfo(uuid);
        if(isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
