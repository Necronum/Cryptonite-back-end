package com.cryptonite.cryptonite.mapper;

import com.cryptonite.cryptonite.domain.localmarket.LocalMarket;
import com.cryptonite.cryptonite.domain.localmarket.LocalMarketDto;
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
public class LocalMarketMapperTestSuite {
    @Autowired
    LocalMarketMapper localMarketMapper;

    @Autowired
    UserRepository userRepository;

    @Test
    void testMapToLocalMarket(){
        User user = prepareUser();
        userRepository.save(user);
        Long userId = user.getId();
        LocalMarketDto localMarketDto = LocalMarketDto.builder()
                .id(1L)
                .userId(userId)
                .coinSymbol("BTC")
                .quantity("10")
                .coinSymbolFor("ETH")
                .quantityFor("100")
                .build();

        LocalMarket localMarket = localMarketMapper.mapToLocalMarket(localMarketDto);
        Long localMarketId = localMarket.getId();
        Long localMarketUserId = localMarket.getUser().getId();
        String localMarketCoin = localMarket.getCoinSymbol();
        String localMarketQuantity = localMarket.getQuantity();
        String localMarketCoinFor = localMarket.getCoinSymbolFor();
        String localMarketQuantityFor = localMarket.getQuantityFor();

        assertEquals(1L, localMarketId);
        assertEquals(userId, localMarketUserId);
        assertEquals("BTC", localMarketCoin);
        assertEquals("10", localMarketQuantity);
        assertEquals("ETH", localMarketCoinFor);
        assertEquals("100", localMarketQuantityFor);

        userRepository.delete(user);
    }

    @Test
    void testMapToLocalMarketDto(){
        User user = prepareUser();
        userRepository.save(user);
        Long userId = user.getId();
        LocalMarket localMarket = LocalMarket.builder()
                .id(1L)
                .user(user)
                .coinSymbol("BTC")
                .quantity("10")
                .coinSymbolFor("ETH")
                .quantityFor("100")
                .build();

        LocalMarketDto localMarketDto = localMarketMapper.mapToLocalMarketDto(localMarket);
        Long localMarketDtoId = localMarketDto.getId();
        Long localMarketDtoUserId = localMarketDto.getUserId();
        String localMarketDtoCoinSymbol = localMarketDto.getCoinSymbol();
        String localMarketDtoQuantity = localMarketDto.getQuantity();
        String localMarketDtoCoinSymbolFor = localMarketDto.getCoinSymbolFor();
        String localMarketDtoQuantityFor = localMarketDto.getQuantityFor();

        assertEquals(1L, localMarketDtoId);
        assertEquals(userId, localMarketDtoUserId);
        assertEquals("BTC", localMarketDtoCoinSymbol);
        assertEquals("10", localMarketDtoQuantity);
        assertEquals("ETH", localMarketDtoCoinSymbolFor);
        assertEquals("100", localMarketDtoQuantityFor);

        userRepository.delete(user);
    }

    @Test
    void testMapToLocalMarketList(){
        User user = prepareUser();
        userRepository.save(user);
        Long userId = user.getId();
        LocalMarketDto localMarketDto = LocalMarketDto.builder()
                .id(1L)
                .userId(userId)
                .coinSymbol("BTC")
                .quantity("10")
                .coinSymbolFor("ETH")
                .quantityFor("100")
                .build();
        List<LocalMarketDto> localMarketDtoList = new ArrayList<>();
        localMarketDtoList.add(localMarketDto);

        List<LocalMarket> localMarketList = localMarketMapper.mapToLocalMarketList(localMarketDtoList);

        assertTrue(localMarketList.size()>0);
        localMarketList.forEach(localMarket -> {
            assertEquals(1L, localMarket.getId());
            assertEquals(userId, localMarket.getUser().getId());
            assertEquals("BTC", localMarket.getCoinSymbol());
            assertEquals("10", localMarket.getQuantity());
            assertEquals("ETH", localMarket.getCoinSymbolFor());
            assertEquals("100", localMarket.getQuantityFor());
        });

        userRepository.delete(user);
    }

    @Test
    void testMapToLocalMarketListDto(){
        User user = prepareUser();
        userRepository.save(user);
        Long userId = user.getId();
        LocalMarket localMarket = LocalMarket.builder()
                .id(1L)
                .user(user)
                .coinSymbol("BTC")
                .quantity("10")
                .coinSymbolFor("ETH")
                .quantityFor("100")
                .build();
        List<LocalMarket> localMarketList = new ArrayList<>();
        localMarketList.add(localMarket);

        List<LocalMarketDto> localMarketDtoList = localMarketMapper.mapToLocalMarketDtoList(localMarketList);

        assertTrue(localMarketDtoList.size()>0);
        localMarketDtoList.forEach(localMarketDto -> {
            assertEquals(1L, localMarketDto.getId());
            assertEquals(userId, localMarketDto.getUserId());
            assertEquals("BTC", localMarketDto.getCoinSymbol());
            assertEquals("10", localMarketDto.getQuantity());
            assertEquals("ETH", localMarketDto.getCoinSymbolFor());
            assertEquals("100", localMarketDto.getQuantityFor());
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
