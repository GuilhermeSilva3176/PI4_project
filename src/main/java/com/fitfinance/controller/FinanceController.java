package com.fitfinance.controller;

import com.fitfinance.domain.Finance;
import com.fitfinance.domain.User;
import com.fitfinance.mapper.FinanceMapper;
import com.fitfinance.request.FinancePostRequest;
import com.fitfinance.request.FinancePutRequest;
import com.fitfinance.response.FinanceGetResponse;
import com.fitfinance.response.FinancePostResponse;
import com.fitfinance.service.FinanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/finances")
@RequiredArgsConstructor
public class FinanceController {
    private final FinanceService financeService;
    private final FinanceMapper mapper;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Finance>> getAllFinances() {
        return ResponseEntity.ok(financeService.getAllFinances());
    }

    @GetMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Finance> findFinanceById(@PathVariable Long id) {
        var financeFound = financeService.findById(id);
        return ResponseEntity.ok(financeFound);
    }

    @GetMapping("/by-user-id")
    public ResponseEntity<List<FinanceGetResponse>> findFinanceByUserId() {
        var user = getUser();
        var financeFound = financeService.findByUserId(user.getId());

        var financesResponse = mapper.toFinanceGetResponses(financeFound);
        return ResponseEntity.ok(financesResponse);
    }

    @PostMapping
    public ResponseEntity<FinancePostResponse> createFinance(@RequestBody @Valid FinancePostRequest financePostRequest) {
        var user = getUser();
        var finance = mapper.toFinance(financePostRequest);
        finance.setUser(user);
        Finance savedFinance = financeService.createFinance(finance);
        var response = mapper.toFinancePostResponse(savedFinance);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private static User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    @PutMapping
    public ResponseEntity<Void> updateFinance(@RequestBody @Valid FinancePutRequest financePutRequest) {
        var user = getUser();
        var financeToUpdate = mapper.toFinance(financePutRequest);
        financeToUpdate.setUser(user);
        financeService.updateFinance(financeToUpdate);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFinance(@PathVariable Long id) {
        financeService.deleteFinance(id, getUser());
        return ResponseEntity.noContent().build();
    }
}
