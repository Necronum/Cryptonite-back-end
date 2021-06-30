package com.cryptonite.cryptonite.domain;

import com.cryptonite.cryptonite.domain.portfolio.Portfolio;
import com.cryptonite.cryptonite.domain.user.User;
import com.cryptonite.cryptonite.repository.PortfolioRepository;
import com.cryptonite.cryptonite.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PortfolioTestSuite {
    @Autowired
    PortfolioRepository portfolioRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void shouldSavePortfolioInDb(){
        Portfolio portfolio = preparePortfolio("BTC", "100");
        portfolioRepository.save(portfolio);

        Long id = portfolio.getId();
        Optional<Portfolio> portfolioFromDb = portfolioRepository.findById(id);

        assertTrue(portfolioFromDb.isPresent());

        portfolioRepository.delete(portfolio);
        userRepository.delete(portfolio.getUser());
    }

    @Test
    public void shouldRemovePortfolioButLeaveUser(){
        Portfolio portfolio = preparePortfolio("BTC", "100");
        portfolioRepository.save(portfolio);

        portfolioRepository.delete(portfolio);
        Long userId = portfolio.getUser().getId();
        Optional<User> userFromRepository = userRepository.findById(userId);

        assertTrue(userFromRepository.isPresent());

        userRepository.delete(portfolio.getUser());
    }

    @Test
    public void shouldUpdatePortfolio(){
        Portfolio portfolio = preparePortfolio("BTC", "100");
        portfolioRepository.save(portfolio);

        Long id = portfolio.getId();
        portfolioRepository.findById(id)
                .ifPresent(foundPortfolio -> {
                    foundPortfolio.setQuantity("10");
                    portfolioRepository.save(foundPortfolio);
                });

        Portfolio portfolioFromDb = portfolioRepository.findById(id).get();

        assertEquals("10", portfolioFromDb.getQuantity());

        portfolioRepository.delete(portfolio);
        userRepository.delete(portfolio.getUser());
    }

    private Portfolio preparePortfolio(String coinSymbol, String quantity){
        User user = User.builder()
                .username("Mart1")
                .password("abcd")
                .email("something@mail.com")
                .wallet(new BigDecimal(1000))
                .build();

        return Portfolio.builder()
                .user(user)
                .coinSymbol(coinSymbol)
                .quantity(quantity)
                .build();
    }
}
