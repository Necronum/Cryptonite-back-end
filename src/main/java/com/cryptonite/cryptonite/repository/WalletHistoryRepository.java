package com.cryptonite.cryptonite.repository;

import com.cryptonite.cryptonite.domain.wallethistory.WalletHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface WalletHistoryRepository extends JpaRepository<WalletHistory, Long> {
}
