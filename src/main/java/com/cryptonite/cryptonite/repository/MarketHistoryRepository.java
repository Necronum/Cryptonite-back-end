package com.cryptonite.cryptonite.repository;

import com.cryptonite.cryptonite.domain.markethistory.MarketHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface MarketHistoryRepository extends JpaRepository<MarketHistory, Long> {
}
