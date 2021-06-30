package com.cryptonite.cryptonite.controller;

import com.cryptonite.cryptonite.domain.user.User;
import com.cryptonite.cryptonite.domain.wallethistory.WalletHistory;
import com.cryptonite.cryptonite.domain.wallethistory.WalletHistoryDto;
import com.cryptonite.cryptonite.mapper.WalletHistoryMapper;
import com.cryptonite.cryptonite.repository.WalletHistoryRepository;
import com.cryptonite.cryptonite.service.WalletHistoryService;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(WalletHistoryController.class)
public class WalletHistoryControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletHistoryService walletHistoryService;

    @MockBean
    private WalletHistoryMapper walletHistoryMapper;

    @MockBean
    private WalletHistoryRepository walletHistoryRepository;


    @Test
    void shouldGetHistories() throws Exception{
        List<WalletHistory> walletHistoryList = new ArrayList<>();
        List<WalletHistoryDto> walletHistoryDtoList = new ArrayList<>();

        when(walletHistoryMapper.mapToWalletHistoryDtoList(walletHistoryList)).thenReturn(walletHistoryDtoList);
        when(walletHistoryService.getWalletHistories()).thenReturn(walletHistoryDtoList);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/wallet/history")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldGetHistory() throws Exception{
        WalletHistory walletHistory = createHistory();
        WalletHistoryDto walletHistoryDto = createHistoryDto();

        when(walletHistoryMapper.mapToWalletHistoryDto(walletHistory)).thenReturn(walletHistoryDto);
        when(walletHistoryService.getWalletHistory(walletHistory.getId())).thenReturn(walletHistoryDto);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/wallet/history/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.walletChange", Matchers.is(1000)));
    }

    private WalletHistory createHistory(){
        return WalletHistory.builder()
                .id(1L)
                .operationDate(LocalDateTime.now())
                .user(new User())
                .walletChange(new BigDecimal(1000))
                .build();
    }

    private WalletHistoryDto createHistoryDto(){
        return WalletHistoryDto.builder()
                .id(1L)
                .operationDate(LocalDateTime.now())
                .userId(1L)
                .walletChange(new BigDecimal(1000))
                .build();
    }
}
