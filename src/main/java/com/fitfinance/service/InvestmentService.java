package com.fitfinance.service;

import com.fitfinance.domain.Investment;
import com.fitfinance.domain.User;
import com.fitfinance.exception.NotFoundException;
import com.fitfinance.repository.InvestmentRepository;
import com.fitfinance.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class InvestmentService {
    private final InvestmentRepository investmentRepository;

    public List<Investment> getAllInvestments() {
        return investmentRepository.findAll();
    }

    public Investment findById(Long id) {
        return investmentRepository.findById(id).orElseThrow(() -> new NotFoundException("Investment not found"));
    }

    public List<Investment> findByUserId(Long userId) {
        return investmentRepository.findByUserId(userId);
    }

    @Transactional
    public Investment createInvestment(Investment investment) {
        return investmentRepository.save(investment);
    }

    public void updateInvestment(Investment investmentToUpdate) {
        Investment foundInvestment = findById(investmentToUpdate.getId());
        foundInvestment.setUser(investmentToUpdate.getUser());

        if (!investmentToUpdate.getUser().getId().equals(foundInvestment.getUser().getId())) {
            throw new SecurityException("User does not have permission");
        }

        investmentRepository.save(investmentToUpdate);
    }

    public void deleteInvestment(Long id, User user) {
        var investment = findById(id);

        if (!investment.getUser().getId().equals(user.getId())) {
            throw new SecurityException("User does not have permission");
        }

        investmentRepository.delete(investment);
    }
}
