package com.fitfinance.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "finance")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Finance {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private double value;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FinanceType type;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate endDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
