package com.cryptonite.cryptonite.domain;

import com.cryptonite.cryptonite.domain.cryptohistory.CryptoHistory;
import com.cryptonite.cryptonite.domain.cryptohistory.OperationStatus;
import com.cryptonite.cryptonite.domain.user.User;
import com.cryptonite.cryptonite.domain.wallethistory.WalletHistory;
import com.cryptonite.cryptonite.repository.UserRepository;
import com.cryptonite.cryptonite.repository.WalletHistoryRepository;
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
public class WalletHistoryTestSuite {
    @Autowired
    WalletHistoryRepository walletHistoryRepository;

    @Autowired
    UserRepository userRepository;


    @Test
    public void shouldSaveCryptoHistoryInDb(){
        WalletHistory walletHistory = prepareHistory(new BigDecimal("1000"));
        walletHistoryRepository.save(walletHistory);

        Long id = walletHistory.getId();
        Optional<WalletHistory> historyFromDb = walletHistoryRepository.findById(id);

        assertTrue(historyFromDb.isPresent());

        walletHistoryRepository.delete(walletHistory);
        userRepository.delete(walletHistory.getUser());
    }

    @Test
    public void shouldRemoveCryptoHistoryButLeaveUser(){
        WalletHistory walletHistory = prepareHistory(new BigDecimal("1000"));
        walletHistoryRepository.save(walletHistory);

        walletHistoryRepository.delete(walletHistory);
        Long userId = walletHistory.getUser().getId();
        Optional<User> userFromRepository = userRepository.findById(userId);

        assertTrue(userFromRepository.isPresent());

        userRepository.delete(walletHistory.getUser());
    }

    @Test
    public void shouldUpdateCryptoHistory(){
        WalletHistory walletHistory = prepareHistory(new BigDecimal("1000"));
        walletHistoryRepository.save(walletHistory);

        Long id = walletHistory.getId();
        walletHistoryRepository.findById(id)
                .ifPresent(foundHistory -> {
                    foundHistory.setWalletChange(new BigDecimal("2000"));
                    walletHistoryRepository.save(foundHistory);
                });

        WalletHistory historyFromDb = walletHistoryRepository.findById(id).get();

        assertEquals("2000.00", historyFromDb.getWalletChange().toString());

        walletHistoryRepository.delete(walletHistory);
        userRepository.delete(walletHistory.getUser());
    }

    private WalletHistory prepareHistory(BigDecimal quantity){
        User user = User.builder()
                .username("Mart1")
                .password("abcd")
                .email("something@mail.com")
                .wallet(new BigDecimal(1000))
                .build();

        return WalletHistory.builder()
                .operationDate(LocalDateTime.now())
                .user(user)
                .walletChange(quantity)
                .build();
    }
}
