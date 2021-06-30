package com.cryptonite.cryptonite.controller;

import com.cryptonite.cryptonite.domain.cryptohistory.CryptoHistory;
import com.cryptonite.cryptonite.domain.cryptohistory.CryptoHistoryDto;
import com.cryptonite.cryptonite.domain.cryptohistory.OperationStatus;
import com.cryptonite.cryptonite.domain.markethistory.MarketHistory;
import com.cryptonite.cryptonite.domain.markethistory.MarketHistoryDto;
import com.cryptonite.cryptonite.domain.user.User;
import com.cryptonite.cryptonite.mapper.MarketHistoryMapper;
import com.cryptonite.cryptonite.repository.MarketHistoryRepository;
import com.cryptonite.cryptonite.service.MarketHistoryService;
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
import org.yaml.snakeyaml.error.Mark;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(MarketHistoryController.class)
public class MarketHistoryControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MarketHistoryService marketHistoryService;

    @MockBean
    private MarketHistoryMapper marketHistoryMapper;

    @MockBean
    private MarketHistoryRepository marketHistoryRepository;


    @Test
    void shouldGetHistories() throws Exception{
        List<MarketHistory> marketHistoryList = new ArrayList<>();
        List<MarketHistoryDto> marketHistoryDtoList = new ArrayList<>();

        when(marketHistoryMapper.mapToMarketHistoryDtoList(marketHistoryList)).thenReturn(marketHistoryDtoList);
        when(marketHistoryService.getMarketHistories()).thenReturn(marketHistoryDtoList);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/market/history")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldGetHistory() throws Exception{
        MarketHistory marketHistory = createHistory();
        MarketHistoryDto marketHistoryDto = createHistoryDto();

        when(marketHistoryMapper.mapToMarketHistoryDto(marketHistory)).thenReturn(marketHistoryDto);
        when(marketHistoryService.getMarketHistory(marketHistory.getId())).thenReturn(marketHistoryDto);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/market/history/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.purchasingCoinSymbol", Matchers.is("BTC")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sellingCoinSymbol", Matchers.is("ETH")));
    }

    private MarketHistory createHistory(){
        return MarketHistory.builder()
                .id(1L)
                .operationDate(LocalDateTime.now())
                .purchasingUser(new User())
                .purchasingCoinSymbol("BTC")
                .purchasingQuantity("100")
                .sellingUser(new User())
                .sellingCoinSymbol("ETH")
                .sellingQuantity("1000")
                .build();
    }

    private MarketHistoryDto createHistoryDto(){
        return MarketHistoryDto.builder()
                .id(1L)
                .operationDate(LocalDateTime.now())
                .purchasingUserId(1L)
                .purchasingCoinSymbol("BTC")
                .purchasingQuantity("100")
                .sellingUserId(2L)
                .sellingCoinSymbol("ETH")
                .sellingQuantity("1000")
                .build();
    }
}
