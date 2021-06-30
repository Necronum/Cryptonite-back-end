package com.cryptonite.cryptonite.domain;

import com.cryptonite.cryptonite.domain.cryptohistory.CryptoHistory;
import com.cryptonite.cryptonite.domain.cryptohistory.OperationStatus;
import com.cryptonite.cryptonite.domain.localmarket.LocalMarket;
import com.cryptonite.cryptonite.domain.markethistory.MarketHistory;
import com.cryptonite.cryptonite.domain.portfolio.Portfolio;
import com.cryptonite.cryptonite.domain.user.User;
import com.cryptonite.cryptonite.domain.wallethistory.WalletHistory;
import com.cryptonite.cryptonite.repository.*;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserTestSuite {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PortfolioRepository portfolioRepository;

    @Autowired
    LocalMarketRepository localMarketRepository;

    @Autowired
    WalletHistoryRepository walletHistoryRepository;

    @Autowired
    MarketHistoryRepository marketHistoryRepository;

    @Autowired
    CryptoHistoryRepository cryptoHistoryRepository;

    @Test
    public void shouldSaveUserInDb(){
        User user = prepareUser();
        userRepository.save(user);

        Long id = user.getId();
        Optional<User> userFromDb = userRepository.findById(id);

        assertTrue(userFromDb.isPresent());

        userRepository.delete(user);
        portfolioRepository.delete(user.getPortfolios().get(0));
        localMarketRepository.delete(user.getMarketOffers().get(0));
        walletHistoryRepository.delete(user.getWalletHistories().get(0));
        marketHistoryRepository.delete(user.getPurchaseMarketHistories().get(0));
        marketHistoryRepository.delete(user.getSellMarketHistories().get(0));
        cryptoHistoryRepository.delete(user.getCryptoHistories().get(0));
        userRepository.delete(user.getPurchaseMarketHistories().get(0).getSellingUser());
    }

    @Test
    public void shouldRemoveUserAndPortfolio(){
        User user = prepareUser();
        userRepository.save(user);

        userRepository.delete(user);
        Long portfolioId = user.getPortfolios().get(0).getId();
        Optional<Portfolio> portfolioFromRepository = portfolioRepository.findById(portfolioId);

        assertFalse(portfolioFromRepository.isPresent());

        marketHistoryRepository.delete(user.getPurchaseMarketHistories().get(0));
        marketHistoryRepository.delete(user.getSellMarketHistories().get(0));
        userRepository.delete(user.getPurchaseMarketHistories().get(0).getSellingUser());
    }

    @Test
    public void shouldRemoveUserAndOffer(){
        User user = prepareUser();
        userRepository.save(user);

        userRepository.delete(user);
        Long offerId = user.getMarketOffers().get(0).getId();
        Optional<LocalMarket> offerFromRepository = localMarketRepository.findById(offerId);

        assertFalse(offerFromRepository.isPresent());

        marketHistoryRepository.delete(user.getPurchaseMarketHistories().get(0));
        marketHistoryRepository.delete(user.getSellMarketHistories().get(0));
        userRepository.delete(user.getPurchaseMarketHistories().get(0).getSellingUser());
    }

    @Test
    public void shouldRemoveUserAndWalletHistory(){
        User user = prepareUser();
        userRepository.save(user);

        userRepository.delete(user);
        Long walletHistoryId = user.getWalletHistories().get(0).getId();
        Optional<WalletHistory> walletHistoryFromRepository = this.walletHistoryRepository.findById(walletHistoryId);

        assertFalse(walletHistoryFromRepository.isPresent());

        marketHistoryRepository.delete(user.getPurchaseMarketHistories().get(0));
        marketHistoryRepository.delete(user.getSellMarketHistories().get(0));
        userRepository.delete(user.getPurchaseMarketHistories().get(0).getSellingUser());
    }

    @Test
    public void shouldRemoveUserAndKeepMarketHistory(){
        User user = prepareUser();
        userRepository.save(user);

        userRepository.delete(user);
        Long buyMarketHistoryId = user.getPurchaseMarketHistories().get(0).getId();
        Optional<MarketHistory> buyHistoryFromRepository = this.marketHistoryRepository.findById(buyMarketHistoryId);
        Long sellMarketHistoryId = user.getSellMarketHistories().get(0).getId();
        Optional<MarketHistory> sellHistoryFromRepository = this.marketHistoryRepository.findById(sellMarketHistoryId);

        assertTrue(buyHistoryFromRepository.isPresent());
        assertTrue(sellHistoryFromRepository.isPresent());

        marketHistoryRepository.delete(user.getPurchaseMarketHistories().get(0));
        marketHistoryRepository.delete(user.getSellMarketHistories().get(0));
        userRepository.delete(user.getPurchaseMarketHistories().get(0).getSellingUser());
    }

    @Test
    public void shouldRemoveUserAndCryptoHistory(){
        User user = prepareUser();
        userRepository.save(user);

        userRepository.delete(user);
        Long cryptoHistoryId = user.getCryptoHistories().get(0).getId();
        Optional<CryptoHistory> cryptoHistoryFromRepository = this.cryptoHistoryRepository.findById(cryptoHistoryId);

        assertFalse(cryptoHistoryFromRepository.isPresent());

        marketHistoryRepository.delete(user.getPurchaseMarketHistories().get(0));
        marketHistoryRepository.delete(user.getSellMarketHistories().get(0));
        userRepository.delete(user.getPurchaseMarketHistories().get(0).getSellingUser());
    }

    @Test
    public void shouldUpdateUser(){
        User user = prepareUser();
        userRepository.save(user);

        Long id = user.getId();
        userRepository.findById(id)
                .ifPresent(foundUser -> {
                    foundUser.setEmail("changed@mail.com");
                    userRepository.save(foundUser);
                });

        User userFromDb = userRepository.findById(id).get();

        assertEquals("changed@mail.com", userFromDb.getEmail());

        userRepository.delete(user);
        portfolioRepository.delete(user.getPortfolios().get(0));
        localMarketRepository.delete(user.getMarketOffers().get(0));
        walletHistoryRepository.delete(user.getWalletHistories().get(0));
        marketHistoryRepository.delete(user.getPurchaseMarketHistories().get(0));
        marketHistoryRepository.delete(user.getSellMarketHistories().get(0));
        cryptoHistoryRepository.delete(user.getCryptoHistories().get(0));
        userRepository.delete(user.getPurchaseMarketHistories().get(0).getSellingUser());
    }

    private User prepareUser(){
        Portfolio portfolio = Portfolio.builder()
                .coinSymbol("BTC")
                .quantity("100")
                .build();
        List<Portfolio> portfolios = new ArrayList<>();
        portfolios.add(portfolio);

        LocalMarket localMarket = LocalMarket.builder()
                .coinSymbol("BTC")
                .quantity("100")
                .coinSymbolFor("ETH")
                .quantityFor("1000")
                .build();
        List<LocalMarket> localMarkets = new ArrayList<>();
        localMarkets.add(localMarket);

        WalletHistory walletHistory = WalletHistory.builder()
                .operationDate(LocalDateTime.now())
                .walletChange(new BigDecimal(1000))
                .build();
        List<WalletHistory> walletHistories = new ArrayList<>();
        walletHistories.add(walletHistory);

        User user = User.builder()
                .username("Mart1")
                .password("abcd")
                .email("something@mail.com")
                .wallet(new BigDecimal(1000))
                .build();

        MarketHistory buyMarketHistory = MarketHistory.builder()
                .operationDate(LocalDateTime.now())
                .purchasingCoinSymbol("BTC")
                .purchasingQuantity("100")
                .sellingUser(user)
                .sellingCoinSymbol("ETH")
                .sellingQuantity("2000")
                .build();
        List<MarketHistory> buyMarketHistories = new ArrayList<>();
        buyMarketHistories.add(buyMarketHistory);

        MarketHistory sellMarketHistory = MarketHistory.builder()
                .operationDate(LocalDateTime.now())
                .purchasingUser(user)
                .purchasingCoinSymbol("BTC")
                .purchasingQuantity("100")
                .sellingCoinSymbol("ETH")
                .sellingQuantity("2000")
                .build();
        List<MarketHistory> sellMarketHistories = new ArrayList<>();
        sellMarketHistories.add(sellMarketHistory);

        CryptoHistory cryptoHistory = CryptoHistory.builder()
                .user(user)
                .coinSymbol("BTC")
                .quantity("100")
                .operationStatus(OperationStatus.PURCHASE)
                .build();
        List<CryptoHistory> cryptoHistories = new ArrayList<>();
        cryptoHistories.add(cryptoHistory);

        return User.builder()
                .username("Mark12")
                .password("oaosda")
                .email("example@mail.com")
                .wallet(new BigDecimal(1000))
                .portfolios(portfolios)
                .marketOffers(localMarkets)
                .walletHistories(walletHistories)
                .purchaseMarketHistories(buyMarketHistories)
                .sellMarketHistories(sellMarketHistories)
                .cryptoHistories(cryptoHistories)
                .build();
    }
}
