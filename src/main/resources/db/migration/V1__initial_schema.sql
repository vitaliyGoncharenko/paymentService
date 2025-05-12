CREATE TABLE payment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount DECIMAL(19, 2) NOT NULL CHECK (amount >= 0.01),
    currency VARCHAR(3) NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    request_id VARCHAR(255) NOT NULL,
    uuid VARCHAR(255) NOT NULL,
    webhook_url VARCHAR(255),
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT uc_request_id UNIQUE (request_id),
    CONSTRAINT uc_uuid UNIQUE (uuid)
);

CREATE INDEX idx_payment_request_id ON payment(request_id);

CREATE TABLE routing_rules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rule_name VARCHAR(255) NOT NULL UNIQUE,
    priority INT NOT NULL,
    is_active BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE routing_rule_conditions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    condition_key VARCHAR(255) NOT NULL,
    operator VARCHAR(50) NOT NULL,
    condition_value VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    routing_rule_id BIGINT NOT NULL,
    CONSTRAINT fk_condition_rule FOREIGN KEY (routing_rule_id) REFERENCES routing_rules(id)
);

CREATE TABLE routing_rule_providers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    routing_rule_id BIGINT NOT NULL,
    provider VARCHAR(50) NOT NULL,
    order_index INT NOT NULL,
    is_active BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_provider_rule FOREIGN KEY (routing_rule_id) REFERENCES routing_rules(id)
);
