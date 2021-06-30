package com.cryptonite.cryptonite.controller;

import com.cryptonite.cryptonite.domain.cryptohistory.CryptoHistory;
import com.cryptonite.cryptonite.domain.cryptohistory.CryptoHistoryDto;
import com.cryptonite.cryptonite.domain.cryptohistory.OperationStatus;
import com.cryptonite.cryptonite.domain.user.User;
import com.cryptonite.cryptonite.mapper.CryptoHistoryMapper;
import com.cryptonite.cryptonite.repository.CryptoHistoryRepository;
import com.cryptonite.cryptonite.service.CryptoHistoryService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(CryptoHistoryController.class)
public class CryptoHistoryControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CryptoHistoryService cryptoHistoryService;

    @MockBean
    private CryptoHistoryMapper cryptoHistoryMapper;

    @MockBean
    private CryptoHistoryRepository cryptoHistoryRepository;


    @Test
    void shouldGetHistories() throws Exception{
        List<CryptoHistory> cryptoHistoryList = new ArrayList<>();
        List<CryptoHistoryDto> cryptoHistoryDtoList = new ArrayList<>();

        when(cryptoHistoryMapper.mapToCryptoHistoryDtoList(cryptoHistoryList)).thenReturn(cryptoHistoryDtoList);
        when(cryptoHistoryService.getCryptoHistories()).thenReturn(cryptoHistoryDtoList);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/crypto/history")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldGetHistory() throws Exception{
        CryptoHistory cryptoHistory = createHistory();
        CryptoHistoryDto cryptoHistoryDto = createHistoryDto();

        when(cryptoHistoryMapper.mapToCryptoHistoryDto(cryptoHistory)).thenReturn(cryptoHistoryDto);
        when(cryptoHistoryService.getCryptoHistoryById(cryptoHistory.getId())).thenReturn(cryptoHistoryDto);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/crypto/history/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coinSymbol", Matchers.is("BTC")));
    }

    private CryptoHistory createHistory(){
        return CryptoHistory.builder()
                .id(1L)
                .operationDate(LocalDateTime.now())
                .user(new User())
                .coinSymbol("BTC")
                .quantity("100")
                .operationStatus(OperationStatus.PURCHASE)
                .build();
    }

    private CryptoHistoryDto createHistoryDto(){
        return CryptoHistoryDto.builder()
                .id(1L)
                .operationDate(LocalDateTime.now())
                .userId(1L)
                .coinSymbol("BTC")
                .quantity("100")
                .operationStatus(OperationStatus.PURCHASE.toString())
                .build();
    }
}
