package com.cryptonite.cryptonite.mapper;

import com.cryptonite.cryptonite.domain.portfolio.Portfolio;
import com.cryptonite.cryptonite.domain.portfolio.PortfolioDto;
import com.cryptonite.cryptonite.exception.ResourceNotFoundException;
import com.cryptonite.cryptonite.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PortfolioMapper {
    private final UserRepository userRepository;

    public Portfolio mapToPortfolio(final PortfolioDto portfolioDto){
        return Portfolio.builder()
                .id(portfolioDto.getId())
                .user(userRepository.findById(portfolioDto.getUserId())
                        .orElseThrow(() -> new ResourceNotFoundException("User with id: " + portfolioDto.getUserId() + " not found")))
                .coinSymbol(portfolioDto.getCoinSymbol())
                .quantity(portfolioDto.getQuantity())
                .build();
    }

    public PortfolioDto mapToPortfolioDto(final Portfolio portfolio){
        return PortfolioDto.builder()
                .id(portfolio.getId())
                .userId(portfolio.getUser().getId())
                .coinSymbol(portfolio.getCoinSymbol())
                .quantity(portfolio.getQuantity())
                .build();
    }

    public List<Portfolio> mapToPortfolioList(List<PortfolioDto> portfolioDtoList){
        return portfolioDtoList.stream()
                .map(this::mapToPortfolio)
                .collect(Collectors.toList());
    }

    public List<PortfolioDto> mapToPortfolioDtoList(List<Portfolio> portfolioList){
        return portfolioList.stream()
                .map(this::mapToPortfolioDto)
                .collect(Collectors.toList());
    }
}
