package com.hvv.payment.repository.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "routing_rules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutingRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String ruleName;

    @Column(name = "priority", nullable = false)
    private Integer priority;

    @ToString.Exclude
    @OneToMany(mappedBy = "routingRule", fetch = FetchType.LAZY)
    private Set<RoutingRuleCondition> conditions;

    @ToString.Exclude
    @OneToMany(mappedBy = "routingRule", fetch = FetchType.LAZY)
    private Set<RoutingRuleProvider> providersChain;

    @Column(nullable = false)
    private Boolean isActive;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
