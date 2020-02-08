INSERT INTO PRODUCT (product_name, current_stock) VALUES
  ('a', 5),
  ('b', 8),
  ('c', 2),
  ('d', 0),
  ('e', 1);

INSERT INTO PRODUCT_RULES (product_name, minimum_stock_level, product_blocked, additional_volume) VALUES
  ('a', 4, FALSE, 0),
  ('b', 4, FALSE, 0),
  ('c', 4, TRUE, 0),
  ('d', 8, FALSE, 15),
  ('e', 4, FALSE, 0);