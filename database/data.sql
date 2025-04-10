INSERT INTO users (first_name, last_name, username, creation_date, last_login, access_level, user_state)
VALUES 
('jaycor', '2025-04-09 23:53:26.827', '2025-04-09 23:58:54.803', 2, 1, '$2a$10$E/b.bbEYLUzBpRfotynfNe4AAaHxFL8Yxu1n0kM1S2NltXKNRlbEy'),
('jaycor1', '2025-04-10 00:04:15.423', NULL, 1, 1, '$2a$10$S2OErHaEz.7XPRvRGdHwFu6lfwbK4wq24UlVDtxdDSVx/2h6Tuq2S');

INSERT INTO product_category (category_name, creation_date, last_upt_date, category_state)
VALUES 
('Electronics', GETDATE(), GETDATE(), 1),
('Books', GETDATE(), GETDATE(), 1),
('Clothing', GETDATE(), GETDATE(), 1);

INSERT INTO products (product_name, category_id, price, creation_date, last_upt_date, product_state)
VALUES 
('Smartphone', 1, 69900, GETDATE(), GETDATE(), 1),
('Novel Book', 2, 1999, GETDATE(), GETDATE(), 1),
('T-Shirt', 3, 2999, GETDATE(), GETDATE(), 1);


INSERT INTO tags (name, tag_state)
VALUES 
('New', 1),
('Sale', 1),
('Limited Edition', 1);


INSERT INTO product_tags (product_id, tag_id) VALUES (1, 1), (1, 2);
INSERT INTO product_tags (product_id, tag_id) VALUES (2, 1);
INSERT INTO product_tags (product_id, tag_id) VALUES (3, 2), (3, 3);
