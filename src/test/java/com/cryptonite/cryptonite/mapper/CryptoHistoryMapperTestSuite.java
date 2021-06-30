package com.cryptonite.cryptonite.mapper;

import com.cryptonite.cryptonite.domain.cryptohistory.CryptoHistory;
import com.cryptonite.cryptonite.domain.cryptohistory.CryptoHistoryDto;
import com.cryptonite.cryptonite.domain.cryptohistory.OperationStatus;
import com.cryptonite.cryptonite.domain.user.User;
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
public class CryptoHistoryMapperTestSuite {
    @Autowired
    CryptoHistoryMapper cryptoHistoryMapper;

    @Autowired
    UserRepository userRepository;

    @Test
    void testMapToWalletHistory(){
        User user = prepareUser();
        userRepository.save(user);
        Long userId = user.getId();
        LocalDateTime now = LocalDateTime.now();
        CryptoHistoryDto cryptoHistoryDto = CryptoHistoryDto.builder()
                .id(1L)
                .operationDate(now)
                .userId(userId)
                .coinSymbol("BTC")
                .quantity("100")
                .operationStatus(OperationStatus.PURCHASE.toString())
                .build();

        CryptoHistory cryptoHistory = cryptoHistoryMapper.mapToCryptoHistory(cryptoHistoryDto);
        Long cryptoHistoryId = cryptoHistory.getId();
        LocalDateTime cryptoHistoryOperationDate = cryptoHistory.getOperationDate();
        Long cryptoHistoryUserId = cryptoHistory.getUser().getId();
        String cryptoHistoryCoinSymbol = cryptoHistory.getCoinSymbol();
        String cryptoHistoryQuantity = cryptoHistory.getQuantity();
        String cryptoHistoryOperationStatus = cryptoHistory.getOperationStatus().toString();

        assertEquals(1L, cryptoHistoryId);
        assertEquals(now, cryptoHistoryOperationDate);
        assertEquals(userId, cryptoHistoryUserId);
        assertEquals("BTC", cryptoHistoryCoinSymbol);
        assertEquals("100", cryptoHistoryQuantity);
        assertEquals(OperationStatus.PURCHASE.toString(), cryptoHistoryOperationStatus);

        userRepository.delete(user);
    }

    @Test
    void testMapToWalletHistoryDto(){
        User user = prepareUser();
        userRepository.save(user);
        Long userId = user.getId();
        LocalDateTime now = LocalDateTime.now();
        CryptoHistory cryptoHistory = CryptoHistory.builder()
                .id(1L)
                .operationDate(now)
                .user(user)
                .coinSymbol("BTC")
                .quantity("100")
                .operationStatus(OperationStatus.PURCHASE)
                .build();

        CryptoHistoryDto cryptoHistoryDto = cryptoHistoryMapper.mapToCryptoHistoryDto(cryptoHistory);
        Long cryptoHistoryDtoId = cryptoHistoryDto.getId();
        LocalDateTime cryptoHistoryDtoOperationDate = cryptoHistoryDto.getOperationDate();
        Long cryptoHistoryDtoUserId = cryptoHistoryDto.getUserId();
        String cryptoHistoryDtoCoinSymbol = cryptoHistoryDto.getCoinSymbol();
        String cryptoHistoryDtoQuantity = cryptoHistoryDto.getQuantity();
        String cryptoHistoryDtoOperationStatus = cryptoHistoryDto.getOperationStatus();

        assertEquals(1L, cryptoHistoryDtoId);
        assertEquals(now, cryptoHistoryDtoOperationDate);
        assertEquals(userId, cryptoHistoryDtoUserId);
        assertEquals("BTC", cryptoHistoryDtoCoinSymbol);
        assertEquals("100", cryptoHistoryDtoQuantity);
        assertEquals(OperationStatus.PURCHASE.toString(), cryptoHistoryDtoOperationStatus);

        userRepository.delete(user);
    }

    @Test
    void testMapToWalletHistoryList(){
        User user = prepareUser();
        userRepository.save(user);
        Long userId = user.getId();
        LocalDateTime now = LocalDateTime.now();
        CryptoHistoryDto cryptoHistoryDto = CryptoHistoryDto.builder()
                .id(1L)
                .operationDate(now)
                .userId(userId)
                .coinSymbol("BTC")
                .quantity("100")
                .operationStatus(OperationStatus.PURCHASE.toString())
                .build();
        List<CryptoHistoryDto> cryptoHistoryDtoList = new ArrayList<>();
        cryptoHistoryDtoList.add(cryptoHistoryDto);

        List<CryptoHistory> cryptoHistoryList = cryptoHistoryMapper.mapToCryptoHistoryList(cryptoHistoryDtoList);

        assertTrue(cryptoHistoryList.size()>0);
        cryptoHistoryList.forEach(cryptoHistory -> {
            assertEquals(1L, cryptoHistory.getId());
            assertEquals(now, cryptoHistory.getOperationDate());
            assertEquals(userId, cryptoHistory.getUser().getId());
            assertEquals("BTC", cryptoHistory.getCoinSymbol());
            assertEquals("100", cryptoHistory.getQuantity());
            assertEquals(OperationStatus.PURCHASE.toString(), cryptoHistory.getOperationStatus().toString());
        });

        userRepository.delete(user);
    }

    @Test
    void testMapToWalletHistoryListDto(){
        User user = prepareUser();
        userRepository.save(user);
        Long userId = user.getId();
        LocalDateTime now = LocalDateTime.now();
        CryptoHistory cryptoHistory = CryptoHistory.builder()
                .id(1L)
                .operationDate(now)
                .user(user)
                .coinSymbol("BTC")
                .quantity("100")
                .operationStatus(OperationStatus.PURCHASE)
                .build();
        List<CryptoHistory> cryptoHistoryList = new ArrayList<>();
        cryptoHistoryList.add(cryptoHistory);

        List<CryptoHistoryDto> cryptoHistoryDtoList = cryptoHistoryMapper.mapToCryptoHistoryDtoList(cryptoHistoryList);

        assertTrue(cryptoHistoryDtoList.size()>0);
        cryptoHistoryDtoList.forEach(cryptoHistoryDto -> {
            assertEquals(1L, cryptoHistoryDto.getId());
            assertEquals(now, cryptoHistoryDto.getOperationDate());
            assertEquals(userId, cryptoHistoryDto.getUserId());
            assertEquals("BTC", cryptoHistoryDto.getCoinSymbol());
            assertEquals("100", cryptoHistoryDto.getQuantity());
            assertEquals(OperationStatus.PURCHASE.toString(), cryptoHistoryDto.getOperationStatus());
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
