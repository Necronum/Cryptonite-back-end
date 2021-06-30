package com.cryptonite.cryptonite.mapper;

import com.cryptonite.cryptonite.domain.cryptohistory.CryptoHistory;
import com.cryptonite.cryptonite.domain.localmarket.LocalMarket;
import com.cryptonite.cryptonite.domain.markethistory.MarketHistory;
import com.cryptonite.cryptonite.domain.portfolio.Portfolio;
import com.cryptonite.cryptonite.domain.user.User;
import com.cryptonite.cryptonite.domain.user.UserDto;
import com.cryptonite.cryptonite.domain.wallethistory.WalletHistory;
import com.cryptonite.cryptonite.repository.UserRepository;
import net.bytebuddy.asm.Advice;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserMapperTestSuite {
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRepository userRepository;

    @Test
    void testMapToUser(){
        UserDto userDto = UserDto.builder()
                .id(1L)
                .username("Mart1")
                .password("abcd")
                .email("something@mail.com")
                .wallet(new BigDecimal(1000))
                .build();
        User user = userMapper.mapToUser(userDto);

        Long userId = user.getId();
        String userUsername = user.getUsername();
        String userPassword = user.getPassword();
        String userEmail = user.getEmail();
        String userWallet = user.getWallet().toString();

        assertEquals(1L, userId);
        assertEquals("Mart1", userUsername);
        assertEquals("abcd", userPassword);
        assertEquals("something@mail.com", userEmail);
        assertEquals("1000", userWallet);
    }

    @Test
    void testMapToUserDto(){
        User user = User.builder()
                .id(1L)
                .username("Mart1")
                .password("abcd")
                .email("something@mail.com")
                .wallet(new BigDecimal(1000))
                .portfolios(preparePortfolio())
                .walletHistories(prepareWalletHistory())
                .marketOffers(prepareMarketOffers())
                .purchaseMarketHistories(prepareMarketHistory())
                .sellMarketHistories(prepareMarketHistory())
                .cryptoHistories(prepareCryptoHistory())
                .build();
        UserDto userDto = userMapper.mapToUserDto(user);

        Long userId = userDto.getId();
        String userUsername = userDto.getUsername();
        String userPassword = userDto.getPassword();
        String userEmail = userDto.getEmail();
        String userWallet = userDto.getWallet().toString();

        assertEquals(1L, userId);
        assertEquals("Mart1", userUsername);
        assertEquals("abcd", userPassword);
        assertEquals("something@mail.com", userEmail);
        assertEquals("1000", userWallet);
        assertTrue(userDto.getPortfolioIds().size()>0);
        assertTrue(userDto.getWalletHistoryIds().size()>0);
        assertTrue(userDto.getPurchaseMarketHistoryIds().size()>0);
        assertTrue(userDto.getSellMarketHistoryIds().size()>0);
        assertTrue(userDto.getCryptoHistoryIds().size()>0);
    }

    @Test
    void testMapToUserList(){
        UserDto userDto = UserDto.builder()
                .id(1L)
                .username("Mart1")
                .password("abcd")
                .email("something@mail.com")
                .wallet(new BigDecimal(1000))
                .build();
        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(userDto);

        List<User> userList = userMapper.mapToUserList(userDtoList);

        assertTrue(userList.size()>0);
        userList.forEach(user -> {
            assertEquals(1L, user.getId());
            assertEquals("Mart1", user.getUsername());
            assertEquals("abcd", user.getPassword());
            assertEquals("something@mail.com", user.getEmail());
            assertEquals("1000", user.getWallet().toString());
        });
    }

    @Test
    void testMapToUserListDto(){
        User user = User.builder()
                .id(1L)
                .username("Mart1")
                .password("abcd")
                .email("something@mail.com")
                .wallet(new BigDecimal(1000))
                .portfolios(preparePortfolio())
                .walletHistories(prepareWalletHistory())
                .marketOffers(prepareMarketOffers())
                .purchaseMarketHistories(prepareMarketHistory())
                .sellMarketHistories(prepareMarketHistory())
                .cryptoHistories(prepareCryptoHistory())
                .build();
        List<User> userList = new ArrayList<>();
        userList.add(user);

        List<UserDto> userDtoList = userMapper.mapToUserDtoList(userList);

        assertTrue(userDtoList.size()>0);
        userDtoList.forEach(userDto -> {
            assertEquals(1L, userDto.getId());
            assertEquals("Mart1", userDto.getUsername());
            assertEquals("abcd", userDto.getPassword());
            assertEquals("something@mail.com", userDto.getEmail());
            assertEquals("1000", userDto.getWallet().toString());
            assertTrue(userDto.getPortfolioIds().size()>0);
            assertTrue(userDto.getWalletHistoryIds().size()>0);
            assertTrue(userDto.getPurchaseMarketHistoryIds().size()>0);
            assertTrue(userDto.getSellMarketHistoryIds().size()>0);
            assertTrue(userDto.getCryptoHistoryIds().size()>0);
        });
    }

    private List<Portfolio> preparePortfolio(){
        Portfolio portfolio = Portfolio.builder()
                .id(1L)
                .build();
        List<Portfolio> portfolioList = new ArrayList<>();
        portfolioList.add(portfolio);
        return portfolioList;
    }

    private List<LocalMarket> prepareMarketOffers(){
        LocalMarket localMarket = LocalMarket.builder()
                .id(1L)
                .build();
        List<LocalMarket> localMarketList = new ArrayList<>();
        localMarketList.add(localMarket);
        return localMarketList;
    }

    private List<WalletHistory> prepareWalletHistory(){
        WalletHistory walletHistory = WalletHistory.builder()
                .id(1L)
                .build();
        List<WalletHistory> walletHistoryList = new ArrayList<>();
        walletHistoryList.add(walletHistory);
        return walletHistoryList;
    }

    private List<MarketHistory> prepareMarketHistory(){
        MarketHistory marketHistory = MarketHistory.builder()
                .id(1L)
                .build();
        List<MarketHistory> marketHistoryList = new ArrayList<>();
        marketHistoryList.add(marketHistory);
        return marketHistoryList;
    }

    private List<CryptoHistory> prepareCryptoHistory(){
        CryptoHistory cryptoHistory = CryptoHistory.builder()
                .id(1L)
                .build();
        List<CryptoHistory> cryptoHistoryList = new ArrayList<>();
        cryptoHistoryList.add(cryptoHistory);
        return cryptoHistoryList;
    }
}
