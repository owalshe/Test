package com.mastercard.evaluation.bin.range.services;

import com.mastercard.evaluation.bin.range.models.BinRangeInfo;

public class CachingBinRangeServiceUpdateResponse {

    private BinRangeInfo binRangeInfo;
    private boolean isUpdated;

    public CachingBinRangeServiceUpdateResponse(BinRangeInfo resource, boolean isUpdated) {
        this.binRangeInfo = binRangeInfo;
        this.isUpdated = isUpdated;
    }

    public BinRangeInfo getResource() {
        return binRangeInfo;
    }

    public boolean isUpdated() {
        return isUpdated;
    }
}
