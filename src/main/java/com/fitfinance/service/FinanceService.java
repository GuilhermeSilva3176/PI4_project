package com.fitfinance.service;

import com.fitfinance.domain.Finance;
import com.fitfinance.domain.User;
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
    private final UserRepository userRepository;

    public List<Finance> getAllFinances() {
        return financeRepository.findAll();
    }

    public Finance findById(Long id) {
        return financeRepository.findById(id).orElseThrow(() -> new NotFoundException("Finance not found"));
    }

    public List<Finance> findByUserId(Long userId) {
        return financeRepository.findByUserId(userId);
    }

    @Transactional
    public Finance createFinance(Finance finance) {
        return financeRepository.save(finance);
    }

    public void updateFinance(Finance financeToUpdate) {
        Finance foundFinance = findById(financeToUpdate.getId());

        if (!financeToUpdate.getUser().getId().equals(foundFinance.getUser().getId())) {
            throw new SecurityException("User does not have permission");
        }

        financeRepository.save(financeToUpdate);
    }

    public void deleteFinance(Long id, User user) {
        var finance = findById(id);

        if (!finance.getUser().getId().equals(user.getId())) {
            throw new SecurityException("User does not have permission");
        }

        financeRepository.delete(finance);
    }
}
