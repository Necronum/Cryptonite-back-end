package com.cryptonite.cryptonite.service;

import com.cryptonite.cryptonite.domain.portfolio.Portfolio;
import com.cryptonite.cryptonite.domain.portfolio.PortfolioDto;
import com.cryptonite.cryptonite.exception.ResourceNotFoundException;
import com.cryptonite.cryptonite.mapper.PortfolioMapper;
import com.cryptonite.cryptonite.repository.PortfolioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioMapper;

    public List<PortfolioDto> getPortfolios(){
        List<Portfolio> portfolios = portfolioRepository.findAll();
        return portfolioMapper.mapToPortfolioDtoList(portfolios);
    }

    public PortfolioDto getPortfolioById(Long id){
        return portfolioRepository.findById(id)
                .map(portfolioMapper::mapToPortfolioDto)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio with id: " + id + " not found"));
    }

    public void addPortfolio(PortfolioDto portfolioDto){
        Portfolio portfolio = portfolioMapper.mapToPortfolio(portfolioDto);
        portfolioRepository.save(portfolio);
    }

    public void deletePortfolio(Long id){
        portfolioRepository.deleteById(id);
    }

    public PortfolioDto updatePortfolio(Long id, PortfolioDto portfolioDto){
        return portfolioRepository.findById(id)
                .map(portfolio -> {
                    Portfolio updatedPortfolio = portfolioMapper.mapToPortfolio(portfolioDto);
                    portfolioRepository.save(updatedPortfolio);
                    return portfolioMapper.mapToPortfolioDto(updatedPortfolio);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio with id: " + id + " not found"));
    }
}
