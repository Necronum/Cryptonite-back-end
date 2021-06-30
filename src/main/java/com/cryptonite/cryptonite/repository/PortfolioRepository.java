package com.cryptonite.cryptonite.repository;

import com.cryptonite.cryptonite.domain.portfolio.Portfolio;
import com.cryptonite.cryptonite.domain.user.User;
import com.cryptonite.cryptonite.domain.user.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}
