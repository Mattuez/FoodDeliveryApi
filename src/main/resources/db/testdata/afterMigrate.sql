SET foreign_key_checks = 0;

DELETE
FROM access_level;
DELETE
FROM access_level_permissions;
DELETE
FROM city;
DELETE
FROM cuisine;
DELETE
FROM payment_method;
DELETE
FROM permission;
DELETE
FROM product;
DELETE
FROM restaurant;
DELETE
FROM restaurant_payment_methods;
DELETE
FROM state;
DELETE
FROM user;
DELETE
FROM user_access_level;
DELETE
FROM restaurant_user_admin;
DELETE
FROM `order`;
DELETE
FROM ordered_product;
DELETE
FROM product_picture;

SET foreign_key_checks = 1;

ALTER TABLE access_level
    AUTO_INCREMENT = 1;
ALTER TABLE access_level_permissions
    AUTO_INCREMENT = 1;
ALTER TABLE city
    AUTO_INCREMENT = 1;
ALTER TABLE cuisine
    AUTO_INCREMENT = 1;;
ALTER TABLE payment_method
    AUTO_INCREMENT = 1;
ALTER TABLE permission
    AUTO_INCREMENT = 1;
ALTER TABLE product
    AUTO_INCREMENT = 1;
ALTER TABLE restaurant
    AUTO_INCREMENT = 1;
ALTER TABLE restaurant_payment_methods
    AUTO_INCREMENT = 1;
ALTER TABLE state
    AUTO_INCREMENT = 1;
ALTER TABLE user
    AUTO_INCREMENT = 1;
ALTER TABLE user_access_level
    AUTO_INCREMENT = 1;
ALTER TABLE restaurant_user_admin
    AUTO_INCREMENT = 1;
ALTER TABLE `order`
    AUTO_INCREMENT = 1;
ALTER TABLE ordered_product
    AUTO_INCREMENT = 1;

INSERT INTO cuisine (id, name)
VALUES (1, 'Brazilian'),
       (2, 'American'),
       (3, 'Chinese'),
       (4, 'Argentinian'),
       (5, 'French'),
       (6, 'Japanese');

INSERT INTO state (id, name)
VALUES (1, 'Paraíba'),
       (2, 'Rio Grande do Sul'),
       (3, 'Rio Grande do Norte'),
       (4, 'Rio de Janeiro'),
       (5, 'São Paulo'),
       (6, 'Acre');

INSERT INTO city (id, name, state_id)
VALUES (1, 'João Pessoa', 1),
       (2, 'Campina Grande', 1),
       (3, 'Porto Alegre', 2),
       (4, 'Natal', 3),
       (5, 'Rio de Janeiro', 4),
       (6, 'São Paulo', 5),
       (7, 'Rio Branco', 6);

INSERT INTO restaurant(id, address_complement, address_district, address_name, address_number, address_postal_code,
                       delivery_fee, name, address_city_id, cuisine_id, register_date, update_date, active, opened)
VALUES (1, null, 'Tambauzinho', 'Av. Pres. Epitácio Pessoa', '2458', '58045-000', 10.00, 'China in Box', 1, 3,
        utc_timestamp, utc_timestamp, true, true),
       (2, null, 'Estados', 'Av. Acre', '109', '58030-230', 8.00, 'Gio Burguer', 1, 2, utc_timestamp, utc_timestamp,
        true, true),
       (3, 'bloco a', 'Catolé', 'Av. Pref. Severino Bezerra Cabral', '1050', '58410-900', 15.00, 'Sal e Brasa', 2, 1,
        utc_timestamp, utc_timestamp, true, true),
       (4, 'bloco b', 'Cidade Baixa', 'R. Sarmento Leite', '969', '90050-170', 0, 'The Raven Restaurant', 3, 1,
        utc_timestamp, utc_timestamp, true, true),
       (5, 'bloco E', 'Manaíra', 'R. Lupércio Branco', '130', '58038-110', 0, 'NAU Frutos do Mar', 1, 1, utc_timestamp,
        utc_timestamp, true, true),
       (6, 'Bloco F', 'Pinheiros', 'R. Argentina', '123', '05445-060', 5, 'El Asador Argentino', 6, 4, utc_timestamp,
        utc_timestamp, true, true),
       (7, 'Av. Champs-Élysées', 'Ipanema', 'R. Belle Cuisine', '42', '22041-080', 10, 'La Belle Cuisine', 5, 5,
        utc_timestamp, utc_timestamp, true, true),
       (8, 'Rua da China', 'Centro', 'Av. Xangai', '88', '69908-400', 7, 'Sabor Chinês', 7, 3, utc_timestamp,
        utc_timestamp, true, true);

INSERT INTO permission (id, name, description)
VALUES  (1, 'EDIT_CUISINES', 'Allows editing cuisines'),
        (2, 'UPDATE_PAYMENT_METHODS', 'Allows creating or editing payment methods'),
        (3, 'EDIT_CITIES', 'Allows creating or editing cities'),
        (4, 'EDIT_STATES', 'Allows creating or editing states'),
        (5, 'VIEW_USERS', 'Allows viewing users'),
        (6, 'EDIT_USERS', 'Allows creating or editing users'),
        (7, 'MANAGE_RESTAURANTS', 'Allows creating, editing or managing restaurants'),
        (8, 'VIEW_ORDERS', 'Allows viewing orders'),
        (9, 'MANAGE_ORDERS', 'Allows managing orders'),
        (10, 'GENERATE_REPORTS', 'Allows generate reports');

INSERT INTO payment_method(id, description)
VALUES (1, 'Cash'),
       (2, 'Credit Card'),
       (3, 'Debit Card'),
       (4, 'PayPal'),
       (5, 'Online Banking'),
       (6, 'Google Pay'),
       (7, 'Apple Pay'),
       (8, 'Venmo'),
       (9, 'Bitcoin');

INSERT INTO restaurant_payment_methods(restaurant_id, payment_method_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (2, 1),
       (2, 2),
       (2, 3),
       (2, 5),
       (3, 1),
       (3, 2),
       (3, 4),
       (3, 7),
       (4, 1),
       (4, 2),
       (4, 3),
       (4, 8),
       (5, 1),
       (5, 2),
       (5, 3),
       (5, 6),
       (6, 1),
       (6, 2),
       (6, 3),
       (6, 9),
       (7, 1),
       (7, 2),
       (7, 3),
       (7, 4),
       (8, 1),
       (8, 2),
       (8, 3),
       (8, 5);

INSERT INTO product(id, name, description, price, restaurant_id, active)
VALUES (1, 'Kung Pao Chicken', 'A classic Chinese dish with chicken, peanuts, and vegetables.', 15.00, 1, true),
       (2, 'Vegetable Fried Rice', 'Delicious fried rice with mixed vegetables.', 12.00, 1, false),
       (3, 'Classic Cheeseburger', 'A juicy beef patty with cheese, lettuce, tomato, and sauce.', 10.00, 2, true),
       (4, 'Crispy Chicken Sandwich', 'Fried chicken breast with lettuce and mayonnaise in a soft bun.', 9.00, 2,
        false),
       (5, 'Picanha Steak', 'Grilled picanha steak served with garlic butter and fries.', 30.00, 3, true),
       (6, 'Mixed Grill Platter', 'A combination of grilled meats, sausages, and vegetables.', 35.00, 3, true),
       (7, 'Blackened Salmon', 'Salmon fillet blackened with Cajun spices and served with rice.', 25.00, 4, true),
       (8, 'Portuguese Style Calamari', 'Calamari rings sautéed in a rich tomato sauce.', 20.00, 4, true),
       (9, 'Lobster Thermidor', 'Lobster meat in a creamy white wine sauce with cheese.', 40.00, 5, false),
       (10, 'Grilled Sea Bass', 'Fresh sea bass grilled to perfection.', 35.00, 5, true),
       (11, 'Argentinian Parrillada', 'An assortment of grilled meats, including ribs, chorizo.', 30.00, 6, true),
       (12, 'Milanesa a la Napolitana', 'Breaded and fried beef or chicken topped with ham, tomato sauce.', 25.00, 6,
        true),
       (13, 'Coq au Vin', 'Classic French dish of chicken braised in red wine.', 28.00, 7, false),
       (14, 'Beef Bourguignon', 'Tender beef stewed with red wine, mushrooms, and bacon.', 32.00, 7, true),
       (15, 'Sweet and Sour Pork', 'Deep-fried pork with sweet and sour sauce', 11.00, 1, true),
       (16, 'Vegetable Spring Rolls', 'Crispy fried rolls with assorted vegetables', 8.50, 1, true),
       (17, 'Chicken Bacon Ranch Wrap', 'Grilled chicken, bacon, and ranch dressing in a wrap', 8.50, 2, true),
       (18, 'Vegetarian Portobello Burger', 'Portobello mushroom burger with veggies', 10.50, 2, true),
       (19, 'Brazilian BBQ Platter', 'Assorted grilled meats with side dishes', 25.00, 3, true),
       (20, 'Seafood Paella', 'Traditional Spanish paella with assorted seafood', 30.00, 3, true),
       (21, 'Grilled Vegetables Skewers', 'Skewers of grilled vegetables with seasoning', 15.00, 3, false);



INSERT INTO access_level (id, name)
VALUES (1, 'Manager'),
       (2, 'Sales Person'),
       (3, 'Secretary'),
       (4, 'Register');


#Adding all permissions to manager
INSERT INTO access_level_permissions(access_level_id, permission_id)
SELECT 1, id FROM permission;

#Adding permission to sales person
INSERT INTO access_level_permissions(access_level_id, permission_id)
SELECT 2, id FROM permission WHERE name LIKE 'VIEW_%';

#Adding permission to secretary
INSERT INTO access_level_permissions(access_level_id, permission_id)
SELECT 3, id FROM permission WHERE name LIKE 'VIEW_%';

#Adding permissions to resgistrar
INSERT INTO access_level_permissions(access_level_id, permission_id)
select 4, id from permission WHERE name LIKE '%_RESTAURANTS' or name like '%_PRODUCTS';

INSERT INTO user(id, email, name, password, register_date)
VALUES (1, 'matheuslins45@gmail.com', 'Matheus', '$2a$10$Ccv8haVOv24.VxSoQTsKPufvSo7Y6.Df9rzjGoP80m1IsJpeQvEYu', utc_timestamp),
       (2, 'mendessruth@gmail.com', 'Ruth', '$2a$12$bB4iFWSAYMi.k..dgPENdu7o6aCJhm6D6WaZuLk3s07Lk9LbqH4.q', utc_timestamp),
       (3, 'ricardo.viana18@gmail.com', 'Ricardo', '$2a$12$uSUiREji1NDpfAe/UwsOk.DATfSdn3aOgvKr2cS0TnexYxdcWV0Qm', utc_timestamp),
       (4, 'example@gmail.com', 'example', '$2a$12$2G/Vv/KTTOoISwsGdjnQIeI7ZZ3ja.ULQ0lKNjvswMoSijwspQnla', utc_timestamp),
       (5, 'restaurantOwner@gmail.com', 'restaurantOwner', '$2a$12$2G/Vv/KTTOoISwsGdjnQIeI7ZZ3ja.ULQ0lKNjvswMoSijwspQnla', utc_timestamp),
       (6, 'client1@gmail.com', 'client', '$2a$12$2G/Vv/KTTOoISwsGdjnQIeI7ZZ3ja.ULQ0lKNjvswMoSijwspQnla', utc_timestamp),
       (7, 'client2@gmail.com', 'client', '$2a$12$2G/Vv/KTTOoISwsGdjnQIeI7ZZ3ja.ULQ0lKNjvswMoSijwspQnla', utc_timestamp);

INSERT INTO user_access_level (user_id, access_level_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4);

INSERT INTO restaurant_user_admin(restaurant_id, user_id)
VALUES (1, 5),
       (2, 5);

-- Insert Order 1
INSERT INTO `order` (id, code, restaurant_id, user_id, payment_method_id, address_city_id, address_postal_code,
                     address_name, address_number, address_complement, address_district, order_status, creation_date,
                     subtotal, delivery_fee, total_amount)
VALUES (1, '95d36b91-4271-4cb7-afaa-614c3c7c1384', 1, 1, 2, 1, '58045-000', 'Av. Pres. Epitácio Pessoa', '2458', NULL,
        'Tambauzinho', 'CREATED', UTC_TIMESTAMP, 30.00, 10.00, 40.00);

INSERT INTO ordered_product (id, quantity, total_price, unit_price, order_id, product_id, note)
    VALUE (1, 2, 30.00, 15.00, 1, 1, 'Spicy version');

INSERT INTO ordered_product (id, quantity, total_price, unit_price, order_id, product_id, note)
    VALUE
    (2, 1, 12.00, 12.00, 1, 2, 'No soy sauce, please');

-- Insert Order 2
INSERT INTO `order` (id, code, restaurant_id, user_id, payment_method_id, address_city_id, address_postal_code,
                     address_name, address_number, address_complement, address_district, order_status, creation_date,
                     subtotal, delivery_fee, total_amount)
VALUES (2, '373ff304-a174-4664-8667-e8bd4c1d9036', 4, 2, 3, 3, '58410-900', 'Av. Pref. Severino Bezerra Cabral', '1050',
        'bloco a', 'Catolé', 'CREATED', UTC_TIMESTAMP, 55.00, 15.00, 70.00);

INSERT INTO ordered_product (id, quantity, total_price, unit_price, order_id, product_id, note)
VALUES (4, 1, 55.00, 55.00, 2, 8, 'Extra cheese');

-- Order 3
INSERT INTO `order` (id, code, restaurant_id, user_id, payment_method_id, address_city_id, address_postal_code,
                     address_name, address_number, order_status, creation_date, subtotal, delivery_fee, total_amount)
VALUES
    (3, '2dc0d39d-4a09-4924-b4f2-2596b26d42d8', 1, 1, 2, 1, '58045-000', 'Av. Pres. Epitácio Pessoa', '2458',
     'CREATED', '2019-11-02 20:35:10', 50.00, 10.00, 60.00);

INSERT INTO ordered_product (quantity, total_price, unit_price, order_id, product_id, note)
VALUES
    (2, 30.00, 15.00, 3, 1, 'Spicy'),
    (1, 12.00, 12.00, 3, 2, 'Extra veggies'),
    (3, 30.00, 10.00, 3, 3, 'Add bacon');

-- Order 4
INSERT INTO `order` (id, code, restaurant_id, user_id, payment_method_id, address_city_id, address_postal_code,
                     address_name, address_number, order_status, creation_date, subtotal, delivery_fee, total_amount)
VALUES
    (4, 'dfc27be4-1dec-43c9-b10d-a9f0b884b0d5', 2, 2, 1, 1, '58030-230', 'Av. Acre', '109', 'CREATED',
     '2019-12-02 20:35:10', 29.00, 8.00, 37.00);

INSERT INTO ordered_product (quantity, total_price, unit_price, order_id, product_id, note)
VALUES
    (2, 20.00, 10.00, 4, 3, 'No mayo'),
    (1, 9.00, 9.00, 4, 4, 'Add cheese'),
    (3, 15.00, 5.00, 4, 5, 'Extra fries');

-- Order 5
INSERT INTO `order` (id, code, restaurant_id, user_id, payment_method_id, address_city_id, address_postal_code,
                     address_name, address_number, order_status, creation_date, subtotal, delivery_fee, total_amount)
VALUES
    (5, '0348fab1-8cbb-43aa-a05b-25a86a20f721', 3, 3, 3, 2, '58410-900', 'Av. Pref. Severino Bezerra Cabral', '1050',
     'CREATED', '2018-12-02 20:35:10', 70.00, 15.00, 85.00);

INSERT INTO ordered_product (quantity, total_price, unit_price, order_id, product_id, note)
VALUES
    (2, 50.00, 25.00, 5, 5, 'Medium rare'),
    (1, 20.00, 20.00, 5, 6, 'Add chimichurri'),
    (3, 45.00, 15.00, 5, 7, 'No onions');

-- Order 6 (Confirmed)
INSERT INTO `order` (id, code, restaurant_id, user_id, payment_method_id, address_city_id, address_postal_code,
                     address_name, address_number, address_complement, address_district, order_status, creation_date,
                     subtotal, delivery_fee, total_amount)
VALUES
    (6, 'abcde123-45f6-789g-ij12-345678k9lm0n', 5, 1, 2, 1, '58038-110', 'R. Lupércio Branco', '130', NULL,
     'Manaíra', 'CREATED', '2022-05-10 12:30:00', 45.00, 0.00, 45.00);

INSERT INTO ordered_product (quantity, total_price, unit_price, order_id, product_id, note)
VALUES
    (2, 30.00, 15.00, 6, 1, 'Spicy'),
    (1, 15.00, 15.00, 6, 2, 'Extra sauce');

-- Order 7 (Confirmed)
INSERT INTO `order` (id, code, restaurant_id, user_id, payment_method_id, address_city_id, address_postal_code,
                     address_name, address_number, address_complement, address_district, order_status, creation_date,
                     subtotal, delivery_fee, total_amount)
VALUES
    (7, 'fghij678-90kl-mn12-op34-5678qrstu90v', 6, 2, 1, 6, '05445-060', 'R. Argentina', '123', 'Bloco F',
     'Pinheiros', 'CREATED', '2022-06-15 15:45:00', 40.00, 5.00, 45.00);

INSERT INTO ordered_product (quantity, total_price, unit_price, order_id, product_id, note)
VALUES
    (2, 25.00, 12.50, 7, 11, 'Medium rare'),
    (1, 15.00, 15.00, 7, 12, 'Add extra cheese');

-- Order 8 (Delivered)
INSERT INTO `order` (id, code, restaurant_id, user_id, payment_method_id, address_city_id, address_postal_code,
                     address_name, address_number, address_complement, address_district, order_status, creation_date,
                     subtotal, delivery_fee, total_amount)
VALUES
    (8, 'mnopq345-6rst-uvwx-yz12-345678abcd9e', 7, 3, 3, 5, '22041-080', 'R. Belle Cuisine', '42', 'Av. Champs-Élysées',
     'Ipanema', 'CREATED', '2022-07-20 18:00:00', 60.00, 10.00, 70.00);

INSERT INTO ordered_product (quantity, total_price, unit_price, order_id, product_id, note)
VALUES
    (2, 25.00, 12.50, 8, 13, 'No onions'),
    (1, 30.00, 30.00, 8, 14, 'Extra bacon');

-- Order 9 (Delivered)
INSERT INTO `order` (id, code, restaurant_id, user_id, payment_method_id, address_city_id, address_postal_code,
                     address_name, address_number, address_complement, address_district, order_status, creation_date,
                     subtotal, delivery_fee, total_amount)
VALUES
    (9, 'qrstv567-8u9w-xyz1-234a-bcdefghijk60', 8, 1, 2, 7, '69908-400', 'Av. Xangai', '88', 'Rua da China',
     'Centro', 'CREATED', '2022-07-21 01:30:00', 28.00, 7.00, 35.00);

INSERT INTO ordered_product (quantity, total_price, unit_price, order_id, product_id, note)
VALUES
    (2, 20.00, 14.00, 9, 15, 'Spicy'),
    (1, 15.00, 15.00, 9, 16, 'No soy sauce');

-- Order 9 (Delivered)
INSERT INTO `order` (id, code, restaurant_id, user_id, payment_method_id, address_city_id, address_postal_code,
                     address_name, address_number, address_complement, address_district, order_status, creation_date,
                     subtotal, delivery_fee, total_amount)
VALUES
    (10, 'qrstv567-8u9w-xyz1-234a-bcdefghijk65', 8, 1, 2, 7, '69908-400', 'Av. Xangai', '88', 'Rua da China',
     'Centro', 'CREATED', '2022-07-21 01:30:00', 28.00, 7.00, 35.00);

INSERT INTO ordered_product (quantity, total_price, unit_price, order_id, product_id, note)
VALUES
    (2, 20.00, 14.00, 9, 15, 'Spicy'),
    (1, 15.00, 15.00, 9, 16, 'No soy sauce'),
    (1, 15.00, 15.00, 9, 16, 'No soy sauce');;
