package com.cryptonite.cryptonite.controller;

import com.cryptonite.cryptonite.domain.portfolio.Portfolio;
import com.cryptonite.cryptonite.domain.portfolio.PortfolioDto;
import com.cryptonite.cryptonite.service.PortfolioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("v1/portfolio")
public class PortfolioController {
    private final PortfolioService portfolioService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PortfolioDto> getAllPortfolios() {
        return portfolioService.getPortfolios();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PortfolioDto getPortfolioById(@PathVariable Long id) {
        return portfolioService.getPortfolioById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addPortfolio(@RequestBody PortfolioDto portfolioDto) {
        portfolioService.addPortfolio(portfolioDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public PortfolioDto updatePortfolio(@PathVariable Long id, @RequestBody PortfolioDto portfolioDto) {
        return portfolioService.updatePortfolio(id, portfolioDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePortfolioById(@PathVariable Long id) {
        portfolioService.deletePortfolio(id);
    }
}
