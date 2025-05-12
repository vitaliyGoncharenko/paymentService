-- Insert the RoutingRule
INSERT INTO routing_rules (id, rule_name, priority, is_active, created_at, updated_at)
VALUES (1, 'USD_CARD_HIGH_AMOUNT', 1, TRUE, NOW(), NOW());

-- Insert conditions for the RoutingRule

-- Condition 1: currency == USD
INSERT INTO routing_rule_conditions (
    id, condition_key, operator, condition_value, is_active, created_at, updated_at, routing_rule_id
)
VALUES
(1, 'currency', 'EQUALS', 'USD', TRUE, NOW(), NOW(), 1);

-- Condition 2: paymentMethod == CARD
INSERT INTO routing_rule_conditions (
    id, condition_key, operator, condition_value, is_active, created_at, updated_at, routing_rule_id
)
VALUES
(2, 'paymentMethod', 'EQUALS', 'CARD', TRUE, NOW(), NOW(), 1);

-- Condition 3: amount > 100
INSERT INTO routing_rule_conditions (
    id, condition_key, operator, condition_value, is_active, created_at, updated_at, routing_rule_id
)
VALUES
(3, 'amount', 'GREATER_THAN', '100', TRUE, NOW(), NOW(), 1);

-- Insert provider chain
INSERT INTO routing_rule_providers (
    id, routing_rule_id, provider, order_index, is_active, created_at, updated_at
)
VALUES
(1, 1, 'PROVIDER_A', 1, TRUE, NOW(), NOW()),
(2, 1, 'PROVIDER_B', 2, TRUE, NOW(), NOW());
