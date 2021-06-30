package com.cryptonite.cryptonite.domain;

import com.cryptonite.cryptonite.domain.cryptohistory.CryptoHistory;
import com.cryptonite.cryptonite.domain.cryptohistory.OperationStatus;
import com.cryptonite.cryptonite.domain.user.User;
import com.cryptonite.cryptonite.repository.CryptoHistoryRepository;
import com.cryptonite.cryptonite.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CryptoHistoryTestSuite {
    @Autowired
    CryptoHistoryRepository cryptoHistoryRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void shouldSaveCryptoHistoryInDb(){
        CryptoHistory cryptoHistory = prepareHistory("BTC", "100");
        cryptoHistoryRepository.save(cryptoHistory);

        Long id = cryptoHistory.getId();
        Optional<CryptoHistory> historyFromDb = cryptoHistoryRepository.findById(id);

        assertTrue(historyFromDb.isPresent());

        cryptoHistoryRepository.delete(cryptoHistory);
        userRepository.delete(cryptoHistory.getUser());
    }

    @Test
    public void shouldRemoveCryptoHistoryButLeaveUser(){
        CryptoHistory cryptoHistory = prepareHistory("BTC", "100");
        cryptoHistoryRepository.save(cryptoHistory);

        cryptoHistoryRepository.delete(cryptoHistory);
        Long userId = cryptoHistory.getUser().getId();
        Optional<User> userFromRepository = userRepository.findById(userId);

        assertTrue(userFromRepository.isPresent());

        userRepository.delete(cryptoHistory.getUser());
    }

    @Test
    public void shouldUpdateCryptoHistory(){
        CryptoHistory cryptoHistory = prepareHistory("BTC", "100");
        cryptoHistoryRepository.save(cryptoHistory);

        Long id = cryptoHistory.getId();
        cryptoHistoryRepository.findById(id)
                .ifPresent(foundHistory -> {
                    foundHistory.setQuantity("10");
                    cryptoHistoryRepository.save(foundHistory);
                });

        CryptoHistory historyFromDb = cryptoHistoryRepository.findById(id).get();

        assertEquals("10", historyFromDb.getQuantity());

        cryptoHistoryRepository.delete(cryptoHistory);
        userRepository.delete(cryptoHistory.getUser());
    }

    private CryptoHistory prepareHistory(String coinSymbol, String quantity){
        User user = User.builder()
                .username("Mart1")
                .password("abcd")
                .email("something@mail.com")
                .wallet(new BigDecimal(1000))
                .build();

        return CryptoHistory.builder()
                .user(user)
                .coinSymbol(coinSymbol)
                .quantity(quantity)
                .operationStatus(OperationStatus.PURCHASE)
                .build();
    }
}
