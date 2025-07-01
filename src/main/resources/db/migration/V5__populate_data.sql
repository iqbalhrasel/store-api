INSERT INTO categories (name) VALUES
('Fruits'),
('Vegetables'),
('Dairy'),
('Bakery'),
('Meat'),
('Beverages');

INSERT INTO products (name, price, description, category_id) VALUES
('Banana', 0.30, 'Fresh organic bananas, rich in potassium.', 1),
('Red Apple', 0.50, 'Crisp and sweet red apples, perfect for snacks.', 1),
('Broccoli', 1.20, 'Fresh green broccoli rich in fiber and vitamins.', 2),
('Carrot', 0.80, 'Crunchy orange carrots, great for cooking or raw snacks.', 2),
('Whole Milk', 2.50, '1-liter full cream milk from local farms.', 3),
('Cheddar Cheese', 3.75, '200g block of aged cheddar cheese.', 3),
('Whole Wheat Bread', 2.00, 'Freshly baked whole wheat sandwich bread.', 4),
('Chicken Breast', 5.99, 'Boneless, skinless chicken breast (500g pack).', 5),
('Orange Juice', 3.25, '1-liter 100% pure squeezed orange juice.', 6),
('Bottled Water', 1.00, '500ml purified bottled drinking water.', 6);
