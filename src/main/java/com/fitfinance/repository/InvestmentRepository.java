package com.fitfinance.repository;

import com.fitfinance.domain.Finance;
import com.fitfinance.domain.FinanceType;
import com.fitfinance.domain.Investment;
import com.fitfinance.domain.InvestmentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    List<Investment> findByNameContaining(String name);
    List<Investment> findByType(InvestmentType type);
    List<Investment> findByUserId(Long id);
}
