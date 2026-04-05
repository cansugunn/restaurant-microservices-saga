-- Siparişlerde kullanılacak view'i Hibernate otomatik tablo olarak yaratacağı için test datası ekleyelim
INSERT INTO product_price_view (id, price) VALUES 
    (101, 50.00), 
    (102, 20.00), 
    (103, 15.00),
    (201, 120.00),
    (202, 80.00),
    (203, 10.00);

-- Orders
INSERT INTO orders (id, tracking_id, customer_id, order_status, restaurant_id, price, failure_messages)
VALUES
    (1, '11111111-1111-1111-1111-111111111111', 'a0000000-0000-0000-0000-000000000001', 'PENDING', 'b0000000-0000-0000-0000-000000000001', 130.00, NULL),
    (2, '22222222-2222-2222-2222-222222222222', 'a0000000-0000-0000-0000-000000000002', 'PENDING', 'b0000000-0000-0000-0000-000000000002', 120.00, NULL);

-- Order Items
INSERT INTO order_items (id, product_id, quantity, price, total_amount, order_id)
VALUES
    (1, 101, 2, 50.00, 100.00, 1),
    (2, 103, 2, 15.00, 30.00, 1),
    (3, 201, 1, 120.00, 120.00, 2);