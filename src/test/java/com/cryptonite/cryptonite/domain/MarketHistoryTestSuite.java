package com.cryptonite.cryptonite.domain;

import com.cryptonite.cryptonite.domain.markethistory.MarketHistory;
import com.cryptonite.cryptonite.domain.user.User;
import com.cryptonite.cryptonite.repository.MarketHistoryRepository;
import com.cryptonite.cryptonite.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MarketHistoryTestSuite {
    @Autowired
    MarketHistoryRepository marketHistoryRepository;

    @Autowired
    UserRepository userRepository;


    @Test
    public void shouldSaveMarketHistoryInDb(){
        MarketHistory marketHistory = prepareHistory("BTC", "100", "1INCH", "1000");
        marketHistoryRepository.save(marketHistory);

        Long id = marketHistory.getId();
        Optional<MarketHistory> historyFromDb = marketHistoryRepository.findById(id);

        assertTrue(historyFromDb.isPresent());

        marketHistoryRepository.delete(marketHistory);
        userRepository.delete(marketHistory.getPurchasingUser());
        userRepository.delete(marketHistory.getSellingUser());
    }

    @Test
    public void shouldRemoveMarketHistoryButLeaveUsers(){
        MarketHistory marketHistory = prepareHistory("BTC", "100", "1INCH", "1000");
        marketHistoryRepository.save(marketHistory);

        marketHistoryRepository.delete(marketHistory);
        Long purchasingUserId = marketHistory.getPurchasingUser().getId();
        Optional<User> purchasingUserFromRepository = userRepository.findById(purchasingUserId);
        Long sellingUserId = marketHistory.getPurchasingUser().getId();
        Optional<User> sellingUserFromRepository = userRepository.findById(sellingUserId);

        assertTrue(purchasingUserFromRepository.isPresent());
        assertTrue(sellingUserFromRepository.isPresent());

        userRepository.delete(marketHistory.getPurchasingUser());
        userRepository.delete(marketHistory.getSellingUser());
    }

    @Test
    public void shouldUpdateMarketHistory(){
        MarketHistory marketHistory = prepareHistory("BTC", "100", "1INCH", "1000");
        marketHistoryRepository.save(marketHistory);

        Long id = marketHistory.getId();
        marketHistoryRepository.findById(id)
                .ifPresent(foundHistory -> {
                    foundHistory.setSellingQuantity("10");
                    marketHistoryRepository.save(foundHistory);
                });

        MarketHistory historyFromDb = marketHistoryRepository.findById(id).get();

        assertEquals("10", historyFromDb.getSellingQuantity());

        marketHistoryRepository.delete(marketHistory);
        userRepository.delete(marketHistory.getPurchasingUser());
        userRepository.delete(marketHistory.getSellingUser());
    }

    private MarketHistory prepareHistory(String purchasingCoinSymbol, String purchasingQuantity, String sellingCoinSymbol, String sellingQuantity){
        User buyingUser = User.builder()
                .username("Mart1")
                .password("abcd")
                .email("something@mail.com")
                .wallet(new BigDecimal(1000))
                .build();

        User sellingUser = User.builder()
                .username("Mart12")
                .password("abcd")
                .email("something1@mail.com")
                .wallet(new BigDecimal(2000))
                .build();

        return MarketHistory.builder()
                .operationDate(LocalDateTime.now())
                .purchasingUser(buyingUser)
                .purchasingCoinSymbol(purchasingCoinSymbol)
                .purchasingQuantity(purchasingQuantity)
                .sellingUser(sellingUser)
                .sellingCoinSymbol(sellingCoinSymbol)
                .sellingQuantity(sellingQuantity)
                .build();
    }
}
