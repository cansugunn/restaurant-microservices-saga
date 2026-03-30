INSERT INTO orders (id, tracking_id, order_status, restaurant_id, price, failure_messages)
VALUES
    (1, '11111111-1111-1111-1111-111111111111', 'PENDING', 10, 45.50, NULL),
    (2, '22222222-2222-2222-2222-222222222222', 'PENDING', 11, 30.00, NULL);

INSERT INTO order_items (id, product_id, quantity, price, total_amount, order_id)
VALUES
    (1, 1001, 2, 10.00, 20.00, 1),
    (2, 1002, 1, 25.50, 25.50, 1),
    (3, 2001, 2, 15.00, 30.00, 2);