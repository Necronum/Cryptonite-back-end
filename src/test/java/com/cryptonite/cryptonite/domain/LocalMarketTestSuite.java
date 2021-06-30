package com.cryptonite.cryptonite.domain;

import com.cryptonite.cryptonite.domain.localmarket.LocalMarket;
import com.cryptonite.cryptonite.domain.user.User;
import com.cryptonite.cryptonite.repository.LocalMarketRepository;
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
public class LocalMarketTestSuite {
    @Autowired
    LocalMarketRepository localMarketRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void shouldSaveOfferInDb(){
        LocalMarket localMarket = prepareOffer("BTC", "100", "1INCH", "500");
        localMarketRepository.save(localMarket);

        Long id = localMarket.getId();
        Optional<LocalMarket> localMarketFromDb = localMarketRepository.findById(id);

        assertTrue(localMarketFromDb.isPresent());

        localMarketRepository.delete(localMarket);
        userRepository.delete(localMarket.getUser());
    }

    @Test
    public void shouldRemoveOfferButLeaveUser(){
        LocalMarket localMarket = prepareOffer("BTC", "100", "1INCH", "500");
        localMarketRepository.save(localMarket);

        localMarketRepository.delete(localMarket);
        Long userId = localMarket.getUser().getId();
        Optional<User> userFromRepository = userRepository.findById(userId);

        assertTrue(userFromRepository.isPresent());

        userRepository.delete(localMarket.getUser());
    }

    @Test
    public void shouldUpdateOffer(){
        LocalMarket localMarket = prepareOffer("BTC", "100", "1INCH", "500");
        localMarketRepository.save(localMarket);

        Long id = localMarket.getId();
        localMarketRepository.findById(id)
                .ifPresent(foundOffer -> {
                    foundOffer.setQuantity("10");
                    localMarketRepository.save(foundOffer);
                });

        LocalMarket offerFromDb = localMarketRepository.findById(id).get();

        assertEquals("10", offerFromDb.getQuantity());

        localMarketRepository.delete(localMarket);
        userRepository.delete(localMarket.getUser());
    }

    private LocalMarket prepareOffer(String coinSymbol, String quantity, String coinSymbolFor, String quantityFor){
        User user = User.builder()
                .username("Mart1")
                .password("abcd")
                .email("something@mail.com")
                .wallet(new BigDecimal(1000))
                .build();

        return LocalMarket.builder()
                .user(user)
                .coinSymbol(coinSymbol)
                .quantity(quantity)
                .coinSymbolFor(coinSymbolFor)
                .quantityFor(quantityFor)
                .build();
    }
}
