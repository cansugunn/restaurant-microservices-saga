-- Restaurants with realistic open/close times
INSERT INTO restaurant (id, name, open_time, close_time)
VALUES
    -- Restaurant 1: Open from 08:00 to 23:00
    ('b0000000-0000-0000-0000-000000000001', 'Burger Queen', '2020-01-01 08:00:00', '2020-01-01 23:00:00'),
    -- Restaurant 2: Open from 10:00 to 22:00
    ('b0000000-0000-0000-0000-000000000002', 'Italiano Pizza', '2020-01-01 10:00:00', '2020-01-01 22:00:00');

-- Products mapping to our Orders test data
INSERT INTO product (id, name, price, restaurant_id)
VALUES
    (101, 'Double Burger', 50.00, 'b0000000-0000-0000-0000-000000000001'),
    (102, 'French Fries', 20.00, 'b0000000-0000-0000-0000-000000000001'),
    (103, 'Coke', 15.00, 'b0000000-0000-0000-0000-000000000001'),
    
    (201, 'Margherita Pizza', 120.00, 'b0000000-0000-0000-0000-000000000002'),
    (202, 'Pesto Pasta', 80.00, 'b0000000-0000-0000-0000-000000000002'),
    (203, 'Ayran', 10.00, 'b0000000-0000-0000-0000-000000000002');
