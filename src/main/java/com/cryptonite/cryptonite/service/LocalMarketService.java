package com.cryptonite.cryptonite.service;

import com.cryptonite.cryptonite.domain.localmarket.LocalMarket;
import com.cryptonite.cryptonite.domain.localmarket.LocalMarketDto;
import com.cryptonite.cryptonite.exception.ResourceNotFoundException;
import com.cryptonite.cryptonite.mapper.LocalMarketMapper;
import com.cryptonite.cryptonite.repository.LocalMarketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class LocalMarketService {
    private final LocalMarketRepository localMarketRepository;
    private final LocalMarketMapper localMarketMapper;

    public List<LocalMarketDto> getOffers(){
        List<LocalMarket> offers = localMarketRepository.findAll();
        return localMarketMapper.mapToLocalMarketDtoList(offers);
    }

    public LocalMarketDto getOfferById(Long id){
        return localMarketRepository.findById(id)
                .map(localMarketMapper::mapToLocalMarketDto)
                .orElseThrow(() -> new ResourceNotFoundException("Offer with id: " + id + " not found"));
    }

    public void addOffer(LocalMarketDto localMarketDto){
        LocalMarket localMarket = localMarketMapper.mapToLocalMarket(localMarketDto);
        localMarketRepository.save(localMarket);
    }

    public void deleteOffer(Long id) {
        localMarketRepository.deleteById(id);
    }

    public LocalMarketDto updateOffer(Long id, LocalMarketDto localMarketDto){
        return localMarketRepository.findById(id)
                .map(offer -> {
                    LocalMarket updatedOffer = localMarketMapper.mapToLocalMarket(localMarketDto);
                    localMarketRepository.save(updatedOffer);
                    return localMarketMapper.mapToLocalMarketDto(updatedOffer);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Offer with id: " + id + " not found"));
    }
}
