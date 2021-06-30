package com.cryptonite.cryptonite.mapper;

import com.cryptonite.cryptonite.domain.markethistory.MarketHistory;
import com.cryptonite.cryptonite.domain.markethistory.MarketHistoryDto;
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
public class MarketHistoryMapperTestSuite {
    @Autowired
    MarketHistoryMapper marketHistoryMapper;

    @Autowired
    UserRepository userRepository;

    @Test
    void testMapToWalletHistory(){
        User buyUser = prepareBuyUser();
        userRepository.save(buyUser);
        Long buyUserId = buyUser.getId();

        User sellUser = prepareSellUser();
        userRepository.save(sellUser);
        Long sellUserId = sellUser.getId();

        LocalDateTime now = LocalDateTime.now();
        MarketHistoryDto marketHistoryDto = MarketHistoryDto.builder()
                .id(1L)
                .operationDate(now)
                .purchasingUserId(buyUserId)
                .purchasingCoinSymbol("BTC")
                .purchasingQuantity("100")
                .sellingUserId(sellUserId)
                .sellingCoinSymbol("BTH")
                .sellingQuantity("10000")
                .build();

        MarketHistory marketHistory = marketHistoryMapper.mapToMarketHistory(marketHistoryDto);
        Long marketHistoryId = marketHistory.getId();
        LocalDateTime marketHistoryOperationDate = marketHistory.getOperationDate();
        Long marketHistoryBuyUserId = marketHistory.getPurchasingUser().getId();
        String marketHistoryPurchasingCoinSymbol = marketHistory.getPurchasingCoinSymbol();
        String marketHistoryPurchasingQuantity = marketHistory.getPurchasingQuantity();
        Long marketHistorySellUserId = marketHistory.getSellingUser().getId();
        String marketHistorySellingCoinSymbol = marketHistory.getSellingCoinSymbol();
        String marketHistorySellingQuantity = marketHistory.getSellingQuantity();


        assertEquals(1L, marketHistoryId);
        assertEquals(now, marketHistoryOperationDate);
        assertEquals(buyUserId, marketHistoryBuyUserId);
        assertEquals("BTC", marketHistoryPurchasingCoinSymbol);
        assertEquals("100", marketHistoryPurchasingQuantity);
        assertEquals(sellUserId, marketHistorySellUserId);
        assertEquals("BTH", marketHistorySellingCoinSymbol);
        assertEquals("10000", marketHistorySellingQuantity);

        userRepository.delete(buyUser);
        userRepository.delete(sellUser);
    }

    @Test
    void testMapToWalletHistoryDto(){
        User buyUser = prepareBuyUser();
        userRepository.save(buyUser);
        Long buyUserId = buyUser.getId();

        User sellUser = prepareSellUser();
        userRepository.save(sellUser);
        Long sellUserId = sellUser.getId();

        LocalDateTime now = LocalDateTime.now();
        MarketHistory marketHistory = MarketHistory.builder()
                .id(1L)
                .operationDate(now)
                .purchasingUser(buyUser)
                .purchasingCoinSymbol("BTC")
                .purchasingQuantity("100")
                .sellingUser(sellUser)
                .sellingCoinSymbol("BTH")
                .sellingQuantity("10000")
                .build();

        MarketHistoryDto marketHistoryDto = marketHistoryMapper.mapToMarketHistoryDto(marketHistory);
        Long marketHistoryDtoId = marketHistoryDto.getId();
        LocalDateTime marketHistoryDtoOperationDate = marketHistoryDto.getOperationDate();
        Long marketHistoryDtoBuyUserId = marketHistoryDto.getPurchasingUserId();
        String marketHistoryDtoPurchasingCoinSymbol = marketHistoryDto.getPurchasingCoinSymbol();
        String marketHistoryDtoPurchasingQuantity = marketHistoryDto.getPurchasingQuantity();
        Long marketHistoryDtoSellUserId = marketHistoryDto.getSellingUserId();
        String marketHistoryDtoSellingCoinSymbol = marketHistoryDto.getSellingCoinSymbol();
        String marketHistoryDtoSellingQuantity = marketHistoryDto.getSellingQuantity();

        assertEquals(1L, marketHistoryDtoId);
        assertEquals(now, marketHistoryDtoOperationDate);
        assertEquals(buyUserId, marketHistoryDtoBuyUserId);
        assertEquals("BTC", marketHistoryDtoPurchasingCoinSymbol);
        assertEquals("100", marketHistoryDtoPurchasingQuantity);
        assertEquals(sellUserId, marketHistoryDtoSellUserId);
        assertEquals("BTH", marketHistoryDtoSellingCoinSymbol);
        assertEquals("10000", marketHistoryDtoSellingQuantity);

        userRepository.delete(buyUser);
        userRepository.delete(sellUser);
    }

    @Test
    void testMapToWalletHistoryList(){
        User buyUser = prepareBuyUser();
        userRepository.save(buyUser);
        Long buyUserId = buyUser.getId();

        User sellUser = prepareSellUser();
        userRepository.save(sellUser);
        Long sellUserId = sellUser.getId();

        LocalDateTime now = LocalDateTime.now();
        MarketHistoryDto marketHistoryDto = MarketHistoryDto.builder()
                .id(1L)
                .operationDate(now)
                .purchasingUserId(buyUserId)
                .purchasingCoinSymbol("BTC")
                .purchasingQuantity("100")
                .sellingUserId(sellUserId)
                .sellingCoinSymbol("BTH")
                .sellingQuantity("10000")
                .build();
        List<MarketHistoryDto> marketHistoryDtoList = new ArrayList<>();
        marketHistoryDtoList.add(marketHistoryDto);


        List<MarketHistory> marketHistoryList = marketHistoryMapper.mapToMarketHistoryList(marketHistoryDtoList);

        assertTrue(marketHistoryList.size()>0);
        marketHistoryList.forEach(marketHistory -> {
            assertEquals(1L, marketHistory.getId());
            assertEquals(now, marketHistory.getOperationDate());
            assertEquals(buyUserId, marketHistory.getPurchasingUser().getId());
            assertEquals("BTC", marketHistory.getPurchasingCoinSymbol());
            assertEquals("100", marketHistory.getPurchasingQuantity());
            assertEquals(sellUserId, marketHistory.getSellingUser().getId());
            assertEquals("BTH", marketHistory.getSellingCoinSymbol());
            assertEquals("10000", marketHistory.getSellingQuantity());
        });

        userRepository.delete(buyUser);
        userRepository.delete(sellUser);
    }

    @Test
    void testMapToWalletHistoryListDto(){
        User buyUser = prepareBuyUser();
        userRepository.save(buyUser);
        Long buyUserId = buyUser.getId();

        User sellUser = prepareSellUser();
        userRepository.save(sellUser);
        Long sellUserId = sellUser.getId();

        LocalDateTime now = LocalDateTime.now();
        MarketHistory marketHistory = MarketHistory.builder()
                .id(1L)
                .operationDate(now)
                .purchasingUser(buyUser)
                .purchasingCoinSymbol("BTC")
                .purchasingQuantity("100")
                .sellingUser(sellUser)
                .sellingCoinSymbol("BTH")
                .sellingQuantity("10000")
                .build();
        List<MarketHistory> marketHistoryList = new ArrayList<>();
        marketHistoryList.add(marketHistory);

        List<MarketHistoryDto> marketHistoryDtoList = marketHistoryMapper.mapToMarketHistoryDtoList(marketHistoryList);

        assertTrue(marketHistoryDtoList.size()>0);
        marketHistoryDtoList.forEach(marketHistoryDto -> {
            assertEquals(1L, marketHistoryDto.getId());
            assertEquals(now, marketHistoryDto.getOperationDate());
            assertEquals(buyUserId, marketHistoryDto.getPurchasingUserId());
            assertEquals("BTC", marketHistoryDto.getPurchasingCoinSymbol());
            assertEquals("100", marketHistoryDto.getPurchasingQuantity());
            assertEquals(sellUserId, marketHistoryDto.getSellingUserId());
            assertEquals("BTH", marketHistoryDto.getSellingCoinSymbol());
            assertEquals("10000", marketHistoryDto.getSellingQuantity());
        });

        userRepository.delete(buyUser);
        userRepository.delete(sellUser);
    }

    private User prepareBuyUser(){
        return User.builder()
                .username("Mart1")
                .password("abcd")
                .email("something@mail.com")
                .wallet(new BigDecimal(1000))
                .build();
    }

    private User prepareSellUser(){
        return User.builder()
                .username("Bort12")
                .password("abcd")
                .email("example@mail.com")
                .wallet(new BigDecimal(2000))
                .build();
    }
}
