package com.cryptonite.cryptonite.repository;

import com.cryptonite.cryptonite.domain.cryptohistory.CryptoHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CryptoHistoryRepository extends JpaRepository<CryptoHistory, Long> {
}
