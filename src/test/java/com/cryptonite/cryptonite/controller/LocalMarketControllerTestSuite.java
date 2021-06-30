package com.cryptonite.cryptonite.controller;

import com.cryptonite.cryptonite.domain.localmarket.LocalMarket;
import com.cryptonite.cryptonite.domain.localmarket.LocalMarketDto;
import com.cryptonite.cryptonite.domain.user.User;
import com.cryptonite.cryptonite.mapper.LocalMarketMapper;
import com.cryptonite.cryptonite.repository.LocalMarketRepository;
import com.cryptonite.cryptonite.service.LocalMarketService;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(LocalMarketController.class)
public class LocalMarketControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocalMarketService localMarketService;

    @MockBean
    private LocalMarketMapper localMarketMapper;

    @MockBean
    private LocalMarketRepository localMarketRepository;


    @Test
    void shouldGetOffers() throws Exception{
        List<LocalMarket> localMarketList = new ArrayList<>();
        List<LocalMarketDto> localMarketDtoList = new ArrayList<>();

        when(localMarketMapper.mapToLocalMarketDtoList(localMarketList)).thenReturn(localMarketDtoList);
        when(localMarketService.getOffers()).thenReturn(localMarketDtoList);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/market")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldGetOffer() throws Exception{
        LocalMarket localMarket = createLocalMarket();
        LocalMarketDto localMarketDto = createLocalMarketDto();

        when(localMarketMapper.mapToLocalMarketDto(localMarket)).thenReturn(localMarketDto);
        when(localMarketService.getOfferById(localMarket.getId())).thenReturn(localMarketDto);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/market/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coinSymbol", Matchers.is("BTC")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity", Matchers.is("100")));
    }

    @Test
    void shouldAddOffer() throws Exception{
        LocalMarket localMarket = createLocalMarket();
        LocalMarketDto localMarketDto = createLocalMarketDto();

        when(localMarketMapper.mapToLocalMarketDto(localMarket)).thenReturn(localMarketDto);
        when(localMarketMapper.mapToLocalMarket(localMarketDto)).thenReturn(localMarket);
        when(localMarketRepository.save(any(LocalMarket.class))).thenReturn(localMarket);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(localMarketDto);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/market")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(201));
    }

    @Test
    void shouldDeleteOffer() throws Exception{
        LocalMarket localMarket = createLocalMarket();
        LocalMarketDto localMarketDto = createLocalMarketDto();

        when(localMarketMapper.mapToLocalMarketDto(localMarket)).thenReturn(localMarketDto);
        when(localMarketService.getOfferById(localMarket.getId())).thenReturn(localMarketDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/market/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Test
    void shouldUpdateOffer() throws Exception{
        LocalMarket localMarket = createLocalMarket();
        LocalMarketDto localMarketDto = createLocalMarketDto();

        when(localMarketMapper.mapToLocalMarketDto(localMarket)).thenReturn(localMarketDto);
        when(localMarketMapper.mapToLocalMarket(localMarketDto)).thenReturn(localMarket);
        when(localMarketRepository.save(any(LocalMarket.class))).thenReturn(localMarket);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(localMarketDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/market/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(202));
    }

    private LocalMarket createLocalMarket(){
        return LocalMarket.builder()
                .id(1L)
                .user(new User())
                .coinSymbol("BTC")
                .quantity("100")
                .coinSymbolFor("ETH")
                .quantityFor("1000")
                .build();
    }

    private LocalMarketDto createLocalMarketDto(){
        return LocalMarketDto.builder()
                .id(1L)
                .userId(1L)
                .coinSymbol("BTC")
                .quantity("100")
                .coinSymbolFor("ETH")
                .quantityFor("1000")
                .build();
    }
}
