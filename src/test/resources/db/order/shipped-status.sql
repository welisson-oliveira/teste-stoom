INSERT INTO public.brand(id, name, sku_code, active)VALUES(nextval('BRAND_SEQ'), 'NIKE', 'NIK', true);
INSERT INTO public.category(id, name, sku_code, active)VALUES(nextval('CATEGORY_SEQ'), 'TENIS', 'TEN', true);
INSERT INTO public.product(id, sku, description, name, price, stock_quantity, brand_id, category_id, active)VALUES(nextval('PRODUCT_SEQ'), 'NIK-0123', 'TENIS-NIKE', 'TENIS', 1000, 10, 1, 1, true);
INSERT INTO public."order"(id, order_date, total_amount, status)VALUES(nextval('ORDER_SEQ'), '2024-01-19 21:34:56', 4000, 'SHIPPED');
INSERT INTO public.order_item(id, quantity, product_id, order_id)VALUES(nextval('ORDER_ITEM_SEQ'), 4, 1, 1);