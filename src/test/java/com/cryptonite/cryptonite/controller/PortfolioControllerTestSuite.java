package com.cryptonite.cryptonite.controller;

import com.cryptonite.cryptonite.domain.portfolio.Portfolio;
import com.cryptonite.cryptonite.domain.portfolio.PortfolioDto;
import com.cryptonite.cryptonite.domain.user.User;
import com.cryptonite.cryptonite.mapper.PortfolioMapper;
import com.cryptonite.cryptonite.repository.PortfolioRepository;
import com.cryptonite.cryptonite.service.PortfolioService;
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
@WebMvcTest(PortfolioController.class)
public class PortfolioControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PortfolioService portfolioService;

    @MockBean
    private PortfolioMapper portfolioMapper;

    @MockBean
    private PortfolioRepository portfolioRepository;

    @Test
    void shouldGetPortfolios() throws Exception{
        List<Portfolio> portfolioList = new ArrayList<>();
        List<PortfolioDto> portfolioDtoList = new ArrayList<>();

        when(portfolioMapper.mapToPortfolioDtoList(portfolioList)).thenReturn(portfolioDtoList);
        when(portfolioService.getPortfolios()).thenReturn(portfolioDtoList);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/portfolio")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldGetPortfolio() throws Exception{
        Portfolio portfolio = createPortfolio();
        PortfolioDto portfolioDto = createPortfolioDto();

        when(portfolioMapper.mapToPortfolioDto(portfolio)).thenReturn(portfolioDto);
        when(portfolioService.getPortfolioById(portfolio.getId())).thenReturn(portfolioDto);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/portfolio/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coinSymbol", Matchers.is("BTC")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity", Matchers.is("100")));
    }

    @Test
    void shouldAddPortfolio() throws Exception{
        Portfolio portfolio = createPortfolio();
        PortfolioDto portfolioDto = createPortfolioDto();

        when(portfolioMapper.mapToPortfolioDto(portfolio)).thenReturn(portfolioDto);
        when(portfolioMapper.mapToPortfolio(portfolioDto)).thenReturn(portfolio);
        when(portfolioRepository.save(any(Portfolio.class))).thenReturn(portfolio);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(portfolioDto);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/portfolio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(201));
    }

    @Test
    void shouldDeletePortfolio() throws Exception{
        Portfolio portfolio = createPortfolio();
        PortfolioDto portfolioDto = createPortfolioDto();

        when(portfolioMapper.mapToPortfolioDto(portfolio)).thenReturn(portfolioDto);
        when(portfolioService.getPortfolioById(portfolio.getId())).thenReturn(portfolioDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/portfolio/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Test
    void shouldUpdatePortfolio() throws Exception{
        Portfolio portfolio = createPortfolio();
        PortfolioDto portfolioDto = createPortfolioDto();

        when(portfolioMapper.mapToPortfolioDto(portfolio)).thenReturn(portfolioDto);
        when(portfolioMapper.mapToPortfolio(portfolioDto)).thenReturn(portfolio);
        when(portfolioRepository.save(any(Portfolio.class))).thenReturn(portfolio);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(portfolioDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/portfolio/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(202));
    }

    private Portfolio createPortfolio(){
        return Portfolio.builder()
                .id(1L)
                .user(new User())
                .coinSymbol("BTC")
                .quantity("100")
                .build();
    }

    private PortfolioDto createPortfolioDto(){
        return PortfolioDto.builder()
                .id(1L)
                .userId(1L)
                .coinSymbol("BTC")
                .quantity("100")
                .build();
    }
}
