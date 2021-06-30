package com.cryptonite.cryptonite.mapper;

import com.cryptonite.cryptonite.domain.portfolio.Portfolio;
import com.cryptonite.cryptonite.domain.portfolio.PortfolioDto;
import com.cryptonite.cryptonite.domain.user.User;
import com.cryptonite.cryptonite.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PortfolioMapperTestSuite {
    @Autowired
    PortfolioMapper portfolioMapper;

    @Autowired
    UserRepository userRepository;

    @Test
    void testMapToPortfolio(){
        User user = prepareUser();
        userRepository.save(user);
        Long userId = user.getId();
        PortfolioDto portfolioDto = PortfolioDto.builder()
                .id(1L)
                .userId(userId)
                .coinSymbol("BTC")
                .quantity("10")
                .build();
        Portfolio portfolio = portfolioMapper.mapToPortfolio(portfolioDto);

        Long portfolioId = portfolio.getId();
        Long portfolioUserId = portfolio.getUser().getId();
        String portfolioCoin = portfolio.getCoinSymbol();
        String portfolioQuantity = portfolio.getQuantity();

        assertEquals(1L, portfolioId);
        assertEquals(userId, portfolioUserId);
        assertEquals("BTC", portfolioCoin);
        assertEquals("10", portfolioQuantity);

        userRepository.delete(user);
    }

    @Test
    void testMapToPortfolioDto(){
        User user = prepareUser();
        userRepository.save(user);
        Long userId = user.getId();
        Portfolio portfolio = Portfolio.builder()
                .id(1L)
                .user(user)
                .coinSymbol("BTC")
                .quantity("10")
                .build();
        PortfolioDto portfolioDto = portfolioMapper.mapToPortfolioDto(portfolio);

        Long portfolioId = portfolioDto.getId();
        Long portfolioUserId = portfolioDto.getUserId();
        String portfolioCoin = portfolioDto.getCoinSymbol();
        String portfolioQuantity = portfolioDto.getQuantity();

        assertEquals(1L, portfolioId);
        assertEquals(userId, portfolioUserId);
        assertEquals("BTC", portfolioCoin);
        assertEquals("10", portfolioQuantity);

        userRepository.delete(user);
    }

    @Test
    void testMapToPortfolioList(){
        User user = prepareUser();
        userRepository.save(user);
        Long userId = user.getId();
        PortfolioDto portfolioDto = PortfolioDto.builder()
                .id(1L)
                .userId(userId)
                .coinSymbol("BTC")
                .quantity("10")
                .build();
        List<PortfolioDto> portfolioDtoList = new ArrayList<>();
        portfolioDtoList.add(portfolioDto);

        List<Portfolio> portfolioList = portfolioMapper.mapToPortfolioList(portfolioDtoList);

        assertTrue(portfolioList.size()>0);
        portfolioList.forEach(portfolio -> {
            assertEquals(1L, portfolio.getId());
            assertEquals(userId, portfolio.getUser().getId());
            assertEquals("BTC", portfolio.getCoinSymbol());
            assertEquals("10", portfolio.getQuantity());
        });

        userRepository.delete(user);
    }

    @Test
    void testMapToPortfolioListDto(){
        User user = prepareUser();
        userRepository.save(user);
        Long userId = user.getId();
        Portfolio portfolio = Portfolio.builder()
                .id(1L)
                .user(user)
                .coinSymbol("BTC")
                .quantity("10")
                .build();
        List<Portfolio> portfolioList = new ArrayList<>();
        portfolioList.add(portfolio);

        List<PortfolioDto> portfolioDtoList = portfolioMapper.mapToPortfolioDtoList(portfolioList);

        assertTrue(portfolioDtoList.size()>0);
        portfolioDtoList.forEach(portfolioDto -> {
            assertEquals(1L, portfolioDto.getId());
            assertEquals(userId, portfolioDto.getUserId());
            assertEquals("BTC", portfolioDto.getCoinSymbol());
            assertEquals("10", portfolioDto.getQuantity());
        });

        userRepository.delete(user);
    }

    private User prepareUser(){
        return User.builder()
                .username("Mart1")
                .password("abcd")
                .email("something@mail.com")
                .wallet(new BigDecimal(1000))
                .build();
    }
}
