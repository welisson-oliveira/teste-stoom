INSERT INTO public.brand(id, name, sku_code, active)VALUES(nextval('BRAND_SEQ'), 'NIKE', 'NIK', true);
INSERT INTO public.brand(id, name, sku_code, active)VALUES(nextval('BRAND_SEQ'), 'PUMA', 'PUM', true);
INSERT INTO public.brand(id, name, sku_code, active)VALUES(nextval('BRAND_SEQ'), 'ADIDAS', 'ADI', true);
INSERT INTO public.category(id, name, sku_code, active)VALUES(nextval('CATEGORY_SEQ'), 'TENIS', 'TEN', true);
INSERT INTO public.product(id, sku, description, name, price, stock_quantity, brand_id, category_id, active)VALUES(nextval('PRODUCT_SEQ'), 'NIK-0121', 'TENIS-NIKE', 'TENIS-NIKE', 1000, 10, 1, 1, true);
INSERT INTO public.product(id, sku, description, name, price, stock_quantity, brand_id, category_id, active)VALUES(nextval('PRODUCT_SEQ'), 'NIK-0122', 'TENIS-PUMA', 'TENIS-PUMA', 1000, 10, 51, 1, true);
INSERT INTO public.product(id, sku, description, name, price, stock_quantity, brand_id, category_id, active)VALUES(nextval('PRODUCT_SEQ'), 'NIK-0123', 'TENIS-PUMA', 'TENIS-PUMA-DISK', 1000, 10, 51, 1, true);
INSERT INTO public.product(id, sku, description, name, price, stock_quantity, brand_id, category_id, active)VALUES(nextval('PRODUCT_SEQ'), 'NIK-0124', 'TENIS-ADIDAS', 'TENIS-ADIDAS', 1000, 10, 101, 1, true);
