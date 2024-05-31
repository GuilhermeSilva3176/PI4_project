package com.fitfinance.repository;

import com.fitfinance.domain.Finance;
import com.fitfinance.domain.FinanceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinanceRepository extends JpaRepository<Finance, Long> {
    List<Finance> findByNameContaining(String name);
    List<Finance> findByType(FinanceType type);
    List<Finance> findByUserId(Long id);
}
