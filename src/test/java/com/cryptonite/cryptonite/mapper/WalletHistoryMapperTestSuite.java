package com.cryptonite.cryptonite.mapper;

import com.cryptonite.cryptonite.domain.user.User;
import com.cryptonite.cryptonite.domain.wallethistory.WalletHistory;
import com.cryptonite.cryptonite.domain.wallethistory.WalletHistoryDto;
import com.cryptonite.cryptonite.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class WalletHistoryMapperTestSuite {
    @Autowired
    WalletHistoryMapper walletHistoryMapper;

    @Autowired
    UserRepository userRepository;

    @Test
    void testMapToWalletHistory(){
        User user = prepareUser();
        userRepository.save(user);
        Long userId = user.getId();
        LocalDateTime now = LocalDateTime.now();
        WalletHistoryDto walletHistoryDto = WalletHistoryDto.builder()
                .id(1L)
                .operationDate(now)
                .userId(userId)
                .walletChange(new BigDecimal(1000))
                .build();

        WalletHistory walletHistory = walletHistoryMapper.mapToWalletHistory(walletHistoryDto);
        Long walletHistoryId = walletHistory.getId();
        LocalDateTime walletHistoryOperationDate = walletHistory.getOperationDate();
        Long walletHistoryUserId = walletHistory.getUser().getId();
        String walletHistoryChange = walletHistory.getWalletChange().toString();

        assertEquals(1L, walletHistoryId);
        assertEquals(now, walletHistoryOperationDate);
        assertEquals(userId, walletHistoryUserId);
        assertEquals("1000", walletHistoryChange);

        userRepository.delete(user);
    }

    @Test
    void testMapToWalletHistoryDto(){
        User user = prepareUser();
        userRepository.save(user);
        Long userId = user.getId();
        LocalDateTime now = LocalDateTime.now();
        WalletHistory walletHistory = WalletHistory.builder()
                .id(1L)
                .operationDate(now)
                .user(user)
                .walletChange(new BigDecimal(1000))
                .build();

        WalletHistoryDto walletHistoryDto = walletHistoryMapper.mapToWalletHistoryDto(walletHistory);
        Long walletHistoryDtoId = walletHistoryDto.getId();
        LocalDateTime walletHistoryDtoOperationDate = walletHistoryDto.getOperationDate();
        Long walletHistoryDtoUserId = walletHistoryDto.getUserId();
        String walletHistoryDtoChange = walletHistoryDto.getWalletChange().toString();

        assertEquals(1L, walletHistoryDtoId);
        assertEquals(now, walletHistoryDtoOperationDate);
        assertEquals(userId, walletHistoryDtoUserId);
        assertEquals("1000", walletHistoryDtoChange);

        userRepository.delete(user);
    }

    @Test
    void testMapToWalletHistoryList(){
        User user = prepareUser();
        userRepository.save(user);
        Long userId = user.getId();
        LocalDateTime now = LocalDateTime.now();
        WalletHistoryDto walletHistoryDto = WalletHistoryDto.builder()
                .id(1L)
                .operationDate(now)
                .userId(userId)
                .walletChange(new BigDecimal(1000))
                .build();
        List<WalletHistoryDto> walletHistoryDtoList = new ArrayList<>();
        walletHistoryDtoList.add(walletHistoryDto);

        List<WalletHistory> walletHistoryList = walletHistoryMapper.mapToWalletHistoryList(walletHistoryDtoList);

        assertTrue(walletHistoryList.size()>0);
        walletHistoryList.forEach(walletHistory -> {
            assertEquals(1L, walletHistory.getId());
            assertEquals(now, walletHistory.getOperationDate());
            assertEquals(userId, walletHistory.getUser().getId());
            assertEquals("1000", walletHistory.getWalletChange().toString());
        });

        userRepository.delete(user);
    }

    @Test
    void testMapToWalletHistoryListDto(){
        User user = prepareUser();
        userRepository.save(user);
        Long userId = user.getId();
        LocalDateTime now = LocalDateTime.now();
        WalletHistory walletHistory = WalletHistory.builder()
                .id(1L)
                .operationDate(now)
                .user(user)
                .walletChange(new BigDecimal(1000))
                .build();
        List<WalletHistory> walletHistoryList = new ArrayList<>();
        walletHistoryList.add(walletHistory);

        List<WalletHistoryDto> walletHistoryDtoList = walletHistoryMapper.mapToWalletHistoryDtoList(walletHistoryList);

        assertTrue(walletHistoryDtoList.size()>0);
        walletHistoryDtoList.forEach(walletHistoryDto -> {
            assertEquals(1L, walletHistoryDto.getId());
            assertEquals(now, walletHistoryDto.getOperationDate());
            assertEquals(userId, walletHistoryDto.getUserId());
            assertEquals("1000", walletHistoryDto.getWalletChange().toString());
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
