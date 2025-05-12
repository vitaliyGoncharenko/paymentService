package com.hvv.payment.repository;

import com.hvv.payment.repository.model.RoutingRule;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoutingRuleRepository extends JpaRepository<RoutingRule, Long> {

    //    @EntityGraph(attributePaths = {"conditions"})
    @EntityGraph(attributePaths = {"providersChain", "conditions"})
//    @EntityGraph
    List<RoutingRule> findByIsActiveTrue();
}
