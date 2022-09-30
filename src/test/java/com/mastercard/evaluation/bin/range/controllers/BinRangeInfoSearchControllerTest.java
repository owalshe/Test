package com.mastercard.evaluation.bin.range.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastercard.evaluation.bin.range.models.BinRangeInfo;
import com.mastercard.evaluation.bin.range.services.BinRangeService;
import com.mastercard.evaluation.bin.range.services.CachingBinRangeServiceUpdateResponse;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BinRangeInfoSearchControllerTest extends BaseControllerTest {

    private static final String TEST_PAN = "4263123412341234";

    private BinRangeService binRangeService = mock(BinRangeService.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private BinRangeInfoSearchController binRangeInfoSearchController;

    @Before
    public void setUp() {
        when(binRangeService.findBinRangeInfoByPan(TEST_PAN)).thenReturn(Optional.of(getMockBinRangeInfo()));
        when(binRangeService.addBinRangeInfo(any(BinRangeInfo.class))).thenReturn(getMockBinRangeInfo());

        ReflectionTestUtils.setField(binRangeInfoSearchController, "binRangeService", binRangeService);
    }

    @Test
    public void testGet() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/binRangeInfoSearch/{pan}", TEST_PAN);

        MockHttpServletResponse response = getMockMvc().perform(builder).andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        BinRangeInfo binRangeInfo = objectMapper.readValue(response.getContentAsString(), BinRangeInfo.class);

        assertNotNull(binRangeInfo);
        assertEquals(new BigDecimal("4263000000000000"), binRangeInfo.getStart());
        assertEquals(new BigDecimal("4263999999999999"), binRangeInfo.getEnd());
        assertEquals("SOME_TEST_BANK", binRangeInfo.getBankName());
        assertEquals("GBP", binRangeInfo.getCurrencyCode());
    }

    @Test
    public void testCreate() throws Exception {
        BinRangeInfo mockBinRangeInfo = getMockBinRangeInfo();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/binRangeInfoSearch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockBinRangeInfo));

        getMockMvc().perform(builder)
                .andExpect(status().isCreated())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(mockBinRangeInfo)));
    }

    @Test
    public void testUpdate() throws Exception {
        BinRangeInfo mockBinRangeInfo = getMockBinRangeInfo();
        UUID ref = UUID.randomUUID();
        mockBinRangeInfo.setRef(ref);
        when(binRangeService.updateBinRangeInfo(anyString(), any(BinRangeInfo.class))).thenReturn(
                new CachingBinRangeServiceUpdateResponse(mockBinRangeInfo, true));
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/binRangeInfoSearch/{uuid}", ref.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockBinRangeInfo));

        getMockMvc().perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(mockBinRangeInfo)));
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        BinRangeInfo mockBinRangeInfo = getMockBinRangeInfo();
        UUID ref = UUID.randomUUID();
        mockBinRangeInfo.setRef(UUID.randomUUID());
        when(binRangeService.updateBinRangeInfo(anyString(), any(BinRangeInfo.class))).thenReturn(
                new CachingBinRangeServiceUpdateResponse(mockBinRangeInfo, false));
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/binRangeInfoSearch/{uuid}", ref.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockBinRangeInfo));

        getMockMvc().perform(builder)
                .andExpect(status().isCreated())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(mockBinRangeInfo)));
    }

    @Test
    public void testDelete() throws Exception {
        when(binRangeService.deleteBinRangeInfo(anyString())).thenReturn(true);
        BinRangeInfo mockBinRangeInfo = getMockBinRangeInfo();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/binRangeInfoSearch/{uuid}", TEST_PAN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockBinRangeInfo));

        getMockMvc().perform(builder)
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        when(binRangeService.deleteBinRangeInfo(anyString())).thenReturn(false);
        BinRangeInfo mockBinRangeInfo = getMockBinRangeInfo();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/binRangeInfoSearch/{pan}", TEST_PAN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockBinRangeInfo));

        getMockMvc().perform(builder)
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    private static BinRangeInfo getMockBinRangeInfo() {
        BinRangeInfo binRangeInfo = new BinRangeInfo();

        binRangeInfo.setStart(new BigDecimal("4263000000000000"));
        binRangeInfo.setEnd(new BigDecimal("4263999999999999"));
        binRangeInfo.setBankName("SOME_TEST_BANK");
        binRangeInfo.setCurrencyCode("GBP");
        return binRangeInfo;
    }
}