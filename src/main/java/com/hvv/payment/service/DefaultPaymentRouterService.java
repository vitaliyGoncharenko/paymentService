package com.hvv.payment.service;

import com.hvv.payment.model.PaymentContext;
import com.hvv.payment.model.Provider;
import com.hvv.payment.repository.RoutingRuleRepository;
import com.hvv.payment.repository.model.RoutingRule;
import com.hvv.payment.repository.model.RoutingRuleProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DefaultPaymentRouterService implements PaymentRouterService {

    private final RoutingRuleRepository routingRuleRepository;

    public List<Provider> route(PaymentContext context) {
        List<RoutingRule> rules = routingRuleRepository.findByIsActiveTrue();

        List<RoutingRule> validRules = rules.stream()
                .filter(rule -> isRuleValid(rule, context))
                .sorted(Comparator.comparingInt(RoutingRule::getPriority))
                .toList();

        if (validRules.isEmpty()) {
            // TODO use default providers list
            log.error("No routing rule matches the provided data.");
            throw new IllegalStateException("No routing rule matches the provided data");
        }

        RoutingRule selectedRule = validRules.getFirst();

        return selectedRule.getProvidersChain().stream()
                .sorted(Comparator.comparingInt(RoutingRuleProvider::getOrderIndex))
                .map(RoutingRuleProvider::getProvider)
                .toList();
    }

    private boolean isRuleValid(RoutingRule rule, PaymentContext paymentContext) {
        // TODO validate how the paymentContext is applicable for rule#getConditions
        return true;
    }

}
