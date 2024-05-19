package com.fitfinance.service;

import com.fitfinance.domain.Finance;
import com.fitfinance.domain.User;
import com.fitfinance.exception.EmailAlreadyExistsException;
import com.fitfinance.exception.NotFoundException;
import com.fitfinance.repository.FinanceRepository;
import com.fitfinance.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinanceService {
    private final FinanceRepository financeRepository;

    public List<Finance> getAllFinances() {
        return financeRepository.findAll();
    }

    public Finance findById(Long id) {
        return financeRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Transactional
    public Finance createFinance(Finance finance) {
        return financeRepository.save(finance);
    }

    public void updateFinance(Finance financeToUpdate) {
        var foundFinance = findById(financeToUpdate.getId());

        financeRepository.save(foundFinance);
    }

    public void deleteFinance(Long id) {
        var finance = findById(id);
        financeRepository.delete(finance);
    }
}
