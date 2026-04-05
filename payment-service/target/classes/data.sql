-- Accounts representing customers used in our realistic flow
INSERT INTO account (id, customer_id, balance)
VALUES
    (1, 'a0000000-0000-0000-0000-000000000001', 99999999.00),
    (2, 'a0000000-0000-0000-0000-000000000002', 0.00),   -- 100 is less than 120 required for Order 2 (tests FAILED scenario)
    (3, 'a0000000-0000-0000-0000-000000000003', 0.00);

-- Existing test payments (if any)
