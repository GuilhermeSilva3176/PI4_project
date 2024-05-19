package com.fitfinance.controller;

import com.fitfinance.domain.Finance;
import com.fitfinance.mapper.FinanceMapper;
import com.fitfinance.request.FinancePostRequest;
import com.fitfinance.request.FinancePutRequest;
import com.fitfinance.response.FinancePostResponse;
import com.fitfinance.service.FinanceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finances")
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
public class FinanceController {
    private final FinanceService financeService;
    private final FinanceMapper mapper;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Finance>> getAllFinances() {
        return ResponseEntity.ok(financeService.getAllFinances());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Finance> findFinanceById(@PathVariable Long id) {
        var financeFound = financeService.findById(id);
        return ResponseEntity.ok(financeFound);
    }

    @PostMapping
    public ResponseEntity<FinancePostResponse> createFinance(@RequestBody @Valid FinancePostRequest financePostRequest) {
        var finance = mapper.toFinance(financePostRequest);
        var response = mapper.toFinancePostResponse(finance);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<Void> updateFinance(@RequestBody @Valid FinancePutRequest financePutRequest) {
        var financeToUpdate = mapper.toFinance(financePutRequest);
        financeService.updateFinance(financeToUpdate);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFinanceById(@PathVariable Long id) {
        financeService.deleteFinance(id);
        return ResponseEntity.noContent().build();
    }
}
