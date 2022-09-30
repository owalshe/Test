package com.mastercard.evaluation.bin.range.controllers;

import com.mastercard.evaluation.bin.range.services.BinRangeService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.mock;

public class BinRangeInfoCacheControllerTest extends BaseControllerTest {

    private BinRangeService binRangeService = mock(BinRangeService.class);

    @Autowired
    private BinRangeInfoCacheController binRangeInfoCacheController;

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(binRangeInfoCacheController, "binRangeService", binRangeService);
    }

    @Test
    @Ignore
    public void testSomething() {
//        TODO - Write tests for new controller operations
    }
}