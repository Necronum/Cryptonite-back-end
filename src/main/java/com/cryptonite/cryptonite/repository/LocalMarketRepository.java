package com.cryptonite.cryptonite.repository;

import com.cryptonite.cryptonite.domain.localmarket.LocalMarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface LocalMarketRepository extends JpaRepository<LocalMarket, Long> {
}
