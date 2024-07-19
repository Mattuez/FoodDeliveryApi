SET foreign_key_checks = 0;

lock tables access_level write, access_level_permissions write, city write, cuisine write,
    payment_method write, permission write, product write, restaurant write,
    restaurant_payment_methods write, state write, user write, user_access_level write,
    restaurant_user_admin write, `order` write, ordered_product write, product_picture write;

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

INSERT INTO cuisine (id, name, color)
VALUES (1, 'Brazilian', 'F2C14E'),
       (2, 'American', 'C24641'),
       (3, 'Chinese', 'F28500'),
       (4, 'Argentinian', '99B433'),
       (5, 'French', '9370DB'),
       (6, 'Japanese', 'F25EA2'),
       (7, 'Italian', 'F2D69B'),
       (8, 'Mexican', 'F29669'),
       (9, 'Indian', 'F2C864'),
       (10, 'Thai', '95E06C'),
       (11, 'Greek', 'F2D1B5'),
       (12, 'Spanish', 'F27EA2'),
       (13, 'Korean', 'F2668B'),
       (14, 'Vietnamese', 'A9E4C9'),
       (15, 'Portuguese', 'F2B099'),
       (16, 'German', 'F29966'),
       (17, 'Peruvian', 'F2C14E'),
       (18, 'Moroccan', 'F29900'),
       (19, 'Turkish', 'F2B099'),
       (20, 'Lebanese', null);

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
                       delivery_fee, name, address_city_id, cuisine_id, register_date, update_date, active, opened,
                       verified)
VALUES (1, null, 'Tambauzinho', 'Av. Pres. Epitácio Pessoa', '2458', '58045-000', 10.00, 'China on plate', 1, 3,
        utc_timestamp, utc_timestamp, true, true, true),
       (2, null, 'Manaira', 'Av. Gov. Flávio Ribeiro Coutinho', '1153', '58038-300', 0, 'Boi na brasa', 1, 1,
        utc_timestamp, utc_timestamp, true, true, false),
       (3, 'Próximo ao Parque Solon de Lucena', 'Centro', 'Rua Duque de Caxias', '125', '58025-450', 5.00,
        'Cantina Italiana', 1, 7, utc_timestamp, utc_timestamp, true, false, false),
       (4, null, 'Cabo Branco', 'Av. Cabo Branco', '3560', '58045-902', 0, 'Sushi Time', 1, 6, utc_timestamp,
        utc_timestamp, true, true, true),
       (5, 'Ao lado do Mangabeira Shopping', 'Mangabeira', 'Rua Josefa Taveira', '850', '58058-020', 7.00,
        'Churrascaria Gaúcha', 1, 1, utc_timestamp, utc_timestamp, false, false, false),
       (6, null, 'Bessa', 'Av. Gov. Argemiro de Figueiredo', '2300', '58037-020', 10.50, 'La Maison', 1, 5,
        utc_timestamp, utc_timestamp, true, true, false),
       (7, 'Dentro do Mag Shopping', 'Manaíra', 'Av. Maria Rosa', '2870', '58038-100', 6.00, 'Padaria Portuguesa', 1,
        15, utc_timestamp, utc_timestamp, true, true, false),
       (8, null, 'Catolé', 'Rua Paulo de Frontin', '358', '58400-120', 4.00, 'O Bode Assado', 2, 1, utc_timestamp,
        utc_timestamp, false, true, false),
       (9, 'Em frente à Praça da Bandeira', 'Centro', 'Rua Venâncio Neiva', '85', '58400-000', 9.50,
        'Restaurante Popular', 2, 1, utc_timestamp, utc_timestamp, true, true, false),
       (10, 'No Shopping Partage', 'Catolé', 'Rua Otacílio Nepomuceno', '1320', '58400-150', 11.00, 'Pizza Mania', 2, 7,
        utc_timestamp, utc_timestamp, true, true, true),
       (11, null, 'Liberdade', 'Rua Vigário Calixto', '450', '58400-200', 5.50, 'Sushi do Zé', 2, 6, utc_timestamp,
        utc_timestamp, true, false, false),
       (12, 'Próximo ao Açude Velho', 'Centro', 'Rua João Suassuna', '280', '58400-010', 8.00, 'Taco Mexicano', 2, 8,
        utc_timestamp, utc_timestamp, false, false, false),
       (13, null, 'Moinhos de Vento', 'Rua Padre Chagas', '327', '90570-080', 15.00, 'Churrascaria do Sul', 3, 1,
        utc_timestamp, utc_timestamp, true, true, true),
       (14, null, 'Cidade Baixa', 'Rua General Lima e Silva', '901', '90050-100', 12.50, 'Galeteria Mama Mia', 3, 7,
        utc_timestamp, utc_timestamp, true, false, false),
       (15, 'No BarraShoppingSul', 'Cristal', 'Av. Diário de Notícias', '300', '91750-420', 0, 'Koh Pee Pee', 3, 10,
        utc_timestamp, utc_timestamp, true, false, false),
       (16, 'Em Ponta Negra', 'Ponta Negra', 'Rua Praia de Ponta Negra', '8888', '59092-100', 13.00,
        'Camarão Delicioso', 4, 1, utc_timestamp, utc_timestamp, true, true, true),
       (17, 'No Midway Mall', ' Tirol', 'Av. Bernardo Vieira', '3775', '59020-500', 7.50, 'China in Box', 4, 3,
        utc_timestamp, utc_timestamp, true, true, false),
       (18, 'No centro histórico', 'Cidade Alta', 'Rua Chile', '30', '59025-140', 6.00, 'Mangai', 4, 1, utc_timestamp,
        utc_timestamp, true, false, false),
       (19, null, 'Copacabana', 'Av. Atlântica', '1702', '22021-001', 20.00, 'Garota de Ipanema', 5, 1, utc_timestamp,
        utc_timestamp, true, true, true),
       (20, 'No Leblon', 'Leblon', 'Rua Dias Ferreira', '510', '22431-050', 25.00, 'Sushi Leblon', 5, 6, utc_timestamp,
        utc_timestamp, true, true, false),
       (21, 'Em Ipanema', 'Ipanema', 'Rua Garcia D''Ávila', '128', '22420-010', 0, 'Zuka', 5, 1, utc_timestamp,
        utc_timestamp, true, false, false),
       (22, 'No Itaim Bibi', 'Itaim Bibi', 'Rua Joaquim Floriano', '541', '04534-011', 22.00, 'Figueira Rubaiyat', 6, 1,
        utc_timestamp, utc_timestamp, true, true, true),
       (23, 'Em Pinheiros', 'Pinheiros', 'Rua dos Pinheiros', '1293', '05422-002', 16.50, 'Mocotó', 6, 1, utc_timestamp,
        utc_timestamp, true, true, false),
       (24, 'Em Moema', 'Moema', 'Av. Ibirapuera', '2927', '04029-200', 0, 'Aoyama - Moema', 6, 6, utc_timestamp,
        utc_timestamp, true, false, false),
       (25, 'No Centro', 'Centro', 'Rua Quinze de Novembro', '35', '69900-120', 5.00, 'Restaurante Sabores da Amazônia',
        7, 1, utc_timestamp, utc_timestamp, false, true, false),
       (26, 'No Bosque', 'Bosque', 'Rua Rui Barbosa', '1245', '69900-000', 8.50, 'Tacacá da Tia', 7, 1, utc_timestamp,
        utc_timestamp, true, true, false),
       (27, 'Próximo ao Mercado Velho', 'Centro', 'Rua Epaminondas Jácome', '220', '69900-100', 6.00,
        'Churrasco do Norte', 7, 1, utc_timestamp, utc_timestamp, true, false, false),
       (28, 'No centro da cidade', 'Centro', 'Avenida Rio Branco', '234', '29010-970', 11.00, 'Cantina da Nonna', 5, 7,
        utc_timestamp, utc_timestamp, true, false, false),
       (29, 'Ao lado do Shopping Vitória', 'Jardim Camburi', 'Avenida Dante Michelini', '3456', '29091-170', 9.50,
        'Sushimar', 5, 6, utc_timestamp, utc_timestamp, true, true, true),
       (30, 'Perto do Parque Moscoso', 'Centro', 'Rua Pedro Palácios', '876', '29015-340', 7.00, 'Acarajé da Bahia', 5,
        1, utc_timestamp, utc_timestamp, false, false, false);

INSERT INTO permission (id, name, description)
VALUES (1, 'EDIT_CUISINES', 'Allows editing cuisines'),
       (2, 'UPDATE_PAYMENT_METHODS',
        'Allows creating or editing payment methods'),
       (3, 'EDIT_CITIES', 'Allows creating or editing cities'),
       (4, 'EDIT_STATES', 'Allows creating or editing states'),
       (5, 'VIEW_USERS', 'Allows viewing users'),
       (6, 'EDIT_USERS', 'Allows creating or editing users'),
       (7, 'MANAGE_RESTAURANTS',
        'Allows creating, editing or managing restaurants'),
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

INSERT INTO product (id, name, description, price, restaurant_id, active)
VALUES (1, 'Frango Xadrez', 'Cubos de frango com legumes ao molho agridoce.', 35.90, 1, true),
       (2, 'Chop Suey de Frango', 'Frango, legumes e broto de feijão ao molho shoyu.', 38.50, 1, true),
       (3, 'Yakisoba Tradicional', 'Macarrão com legumes, frango e carne ao molho yakisoba.', 42.00, 1, true),
       (4, 'Rolinho Primavera', 'Massa crocante recheada com legumes e carne.', 15.00, 1, true),
       (5, 'Porção de Guioza', 'Pastelzinho chinês cozido no vapor, recheado com carne e legumes.', 22.00,
        1, true),
       (6, 'Arroz Chop Suey', 'Arroz frito com legumes, ovo e presunto.', 25.00, 1, true),
       (7, 'Frango com Amêndoas', 'Cubos de frango empanados com amêndoas laminadas.', 39.90, 1, true),
       (8, 'Carne com Brócolis', 'Tiras de carne com brócolis ao molho shoyu.', 45.00, 1, true),
       (9, 'Lombo Agridoce', 'Cubos de lombo suíno empanados ao molho agridoce.', 42.50, 1, true),
       (10, 'Camarão com Legumes', 'Camarão refogado com legumes ao molho shoyu.', 55.00, 1, true),
       (11, 'Yakimeshi', 'Arroz frito com legumes, ovo e frango.', 28.00, 1, true),
       (12, 'Macarrão com Camarão', 'Macarrão com camarão e legumes ao molho branco.', 48.00, 1, true),
       (13, 'Frango com Gergelim', 'Cubos de frango empanados com gergelim.', 37.50, 1, true),
       (14, 'Carne com Pimentão', 'Tiras de carne com pimentão e cebola ao molho shoyu.', 43.00, 1, true),
       (15, 'Costela Agridoce', 'Costela de porco ao molho agridoce.', 49.90, 1, true),
       (16, 'Lula à Dorê', 'Lula empanada e frita.', 52.00, 1, true),
       (17, 'Arroz Frito Especial', 'Arroz frito com camarão, legumes e ovo.', 32.00, 1, true),
       (18, 'Macarrão com Frango', 'Macarrão com frango e legumes ao molho branco.', 35.00, 1, true),
       (19, 'Frango com Castanha de Caju', 'Cubos de frango com castanha de caju.', 41.00, 1, true),
       (20, 'Carne com Champignon', 'Tiras de carne com champignon ao molho shoyu.', 46.50, 1, true),
       (21, 'Picanha na Brasa', 'Picanha bovina grelhada na brasa, acompanhada de arroz, feijão, farofa e vinagrete.',
        59.90, 2, true),
       (22, 'Lasanha à Bolonhesa', 'Lasanha tradicional com molho à bolonhesa, queijo mussarela e parmesão.', 38.00, 3,
        true),
       (23, 'Combinado de Sushi', 'Seleção de sushis e sashimis variados.', 65.00, 4, true),
       (24, 'Churrasco Completo',
        'Picanha, maminha, linguiça, frango e costela, acompanhados de arroz, feijão, farofa e vinagrete.', 89.90, 5,
        true),
       (25, 'Coq au Vin', 'Frango cozido em vinho tinto com cogumelos, bacon e cebola.', 52.00, 6, true),
       (26, 'Pastel de Nata', 'Pastel de nata tradicional português.', 7.50, 7, true),
       (27, 'Carneiro Guisado', 'Carneiro cozido lentamente em molho com especiarias e legumes.', 45.00, 8, true),
       (28, 'Prato Feito', 'Arroz, feijão, carne, salada e ovo frito.', 18.00, 9, true),
       (29, 'Pizza Calabresa', 'Pizza com molho de tomate, mussarela e calabresa.', 35.00, 10, true),
       (30, 'Temaki de Salmão', 'Cone de alga recheado com arroz, salmão e cream cheese.', 18.50, 11, true);

INSERT INTO access_level (id, name)
VALUES (1, 'Manager'),
       (2, 'Sales Person'),
       (3, 'Secretary'),
       (4, 'Register');


#Adding all permissions to manager
INSERT INTO access_level_permissions(access_level_id, permission_id)
SELECT 1, id
FROM permission;

#Adding permission to sales person
INSERT INTO access_level_permissions(access_level_id, permission_id)
SELECT 2, id
FROM permission
WHERE name LIKE 'VIEW_%';

#Adding permission to secretary
INSERT INTO access_level_permissions(access_level_id, permission_id)
SELECT 3, id
FROM permission
WHERE name LIKE 'VIEW_%';

#Adding permissions to resgistrar
INSERT INTO access_level_permissions(access_level_id, permission_id)
select 4, id
from permission
WHERE name LIKE '%_RESTAURANTS'
   or name like '%_PRODUCTS';

INSERT INTO user(id, email, name, password, register_date)
VALUES (1, 'matheuslins45@gmail.com', 'Matheus', '$2a$10$Ccv8haVOv24.VxSoQTsKPufvSo7Y6.Df9rzjGoP80m1IsJpeQvEYu',
        utc_timestamp),
       (2, 'mendessruth@gmail.com', 'Ruth', '$2a$12$bB4iFWSAYMi.k..dgPENdu7o6aCJhm6D6WaZuLk3s07Lk9LbqH4.q',
        utc_timestamp),
       (3, 'ricardo.viana18@gmail.com', 'Ricardo', '$2a$12$uSUiREji1NDpfAe/UwsOk.DATfSdn3aOgvKr2cS0TnexYxdcWV0Qm',
        utc_timestamp),
       (4, 'example@gmail.com', 'example', '$2a$12$2G/Vv/KTTOoISwsGdjnQIeI7ZZ3ja.ULQ0lKNjvswMoSijwspQnla',
        utc_timestamp),
       (5, 'restaurantOwner@gmail.com', 'restaurantOwner',
        '$2a$12$f.R12jtrTyDRHaalqY36k.tpKEwAfiDCW0LAPwu6xbFmSLyI6BWKO', utc_timestamp),
       (6, 'client1@gmail.com', 'client', '$2a$12$f.R12jtrTyDRHaalqY36k.tpKEwAfiDCW0LAPwu6xbFmSLyI6BWKO',
        utc_timestamp),
       (7, 'client2@gmail.com', 'client', '$2a$12$f.R12jtrTyDRHaalqY36k.tpKEwAfiDCW0LAPwu6xbFmSLyI6BWKO',
        utc_timestamp);

INSERT INTO user_access_level (user_id, access_level_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4);

INSERT INTO restaurant_user_admin(restaurant_id, user_id)
VALUES (1, 5),
       (2, 5);

INSERT INTO `order` (id, code, address_complement, address_district, address_name, address_number, address_postal_code,
                     cancellation_date, confirmation_date, creation_date, delivery_date, delivery_fee, subtotal,
                     total_amount, address_city_id, payment_method_id, restaurant_id, user_id, order_status)
VALUES (1, UUID(), null, 'Tambauzinho', 'Av. Pres. Epitácio Pessoa', '2458', '58045-000', null, utc_timestamp,
        utc_timestamp, utc_timestamp, 10.00, 116.40, 126.40, 1, 1, 1, 1, 'DELIVERED'),
       (2, UUID(), null, 'Tambauzinho', 'Av. Pres. Epitácio Pessoa', '2458', '58045-000', null, utc_timestamp,
        utc_timestamp, utc_timestamp, 10.00, 125.90, 135.90, 1, 1, 1, 1, 'DELIVERED'),
       (3, UUID(), null, 'Tambauzinho', 'Av. Pres. Epitácio Pessoa', '2458', '58045-000', null, utc_timestamp,
        utc_timestamp, utc_timestamp, 10.00, 103.40, 113.40, 1, 1, 1, 1, 'DELIVERED'),
       (4, UUID(), null, 'Tambauzinho', 'Av. Pres. Epitácio Pessoa', '2458', '58045-000', null, utc_timestamp,
        utc_timestamp, utc_timestamp, 10.00, 119.40, 129.40, 1, 1, 1, 1, 'CANCELED'),
       (5, UUID(), null, 'Tambauzinho', 'Av. Pres. Epitácio Pessoa', '2458', '58045-000', null, utc_timestamp,
        utc_timestamp, utc_timestamp, 10.00, 120.50, 130.50, 1, 1, 1, 1, 'DELIVERED'),
       (6, UUID(), null, 'Tambauzinho', 'Av. Pres. Epitácio Pessoa', '2458', '58045-000', null, utc_timestamp,
        utc_timestamp, utc_timestamp, 10.00, 95.40, 105.40, 1, 1, 1, 1, 'DELIVERED'),
       (7, UUID(), null, 'Tambauzinho', 'Av. Pres. Epitácio Pessoa', '2458', '58045-000', null, utc_timestamp,
        utc_timestamp, utc_timestamp, 10.00, 110.40, 120.40, 1, 1, 1, 1, 'DELIVERED'),
       (8, UUID(), null, 'Tambauzinho', 'Av. Pres. Epitácio Pessoa', '2458', '58045-000', null, utc_timestamp,
        utc_timestamp, utc_timestamp, 10.00, 125.90, 135.90, 1, 1, 1, 1, 'DELIVERED'),
       (9, UUID(), null, 'Tambauzinho', 'Av. Pres. Epitácio Pessoa', '2458', '58045-000', null, utc_timestamp,
        utc_timestamp, utc_timestamp, 10.00, 100.40, 110.40, 1, 1, 1, 1, 'DELIVERED'),
       (10, UUID(), null, 'Tambauzinho', 'Av. Pres. Epitácio Pessoa', '2458', '58045-000', null, utc_timestamp,
        utc_timestamp, utc_timestamp, 10.00, 113.40, 123.40, 1, 1, 1, 1, 'CANCELED');

-- Pedido 1 (total_amount: 126.40)
INSERT INTO ordered_product (id, quantity, total_price, unit_price, order_id, product_id, note)
VALUES (1, 1, 35.90, 35.90, 1, 1, 'Sem pimenta'),
       (2, 2, 84.00, 42.00, 1, 3, null),
       (3, 1, 8.50, 8.50, 1, 4, null);

-- Pedido 2 (total_amount: 135.90)
INSERT INTO ordered_product (id, quantity, total_price, unit_price, order_id, product_id, note)
VALUES (4, 1, 39.90, 39.90, 2, 7, null),
       (5, 1, 45.00, 45.00, 2, 8, 'Bem passado'),
       (6, 1, 41.00, 41.00, 2, 19, null);

-- Pedido 3 (total_amount: 113.40)
INSERT INTO ordered_product (id, quantity, total_price, unit_price, order_id, product_id, note)
VALUES (7, 1, 42.50, 42.50, 3, 9, null),
       (8, 1, 55.00, 55.00, 3, 10, 'Pouco picante'),
       (9, 1, 5.90, 5.90, 3, 4, null);

-- Pedido 4 (total_amount: 129.40)
INSERT INTO ordered_product (id, quantity, total_price, unit_price, order_id, product_id, note)
VALUES (10, 1, 42.00, 42.00, 4, 3, null),
       (11, 1, 37.50, 37.50, 4, 13, 'Com bastante gergelim'),
       (12, 2, 40.00, 20.00, 4, 5, null);

-- Pedido 5 (total_amount: 130.50)
INSERT INTO ordered_product (id, quantity, total_price, unit_price, order_id, product_id, note)
VALUES (13, 1, 43.00, 43.00, 5, 14, 'Sem cebola'),
       (14, 1, 49.90, 49.90, 5, 15, null),
       (15, 1, 27.60, 27.60, 5, 6, null);

-- Pedido 6 (total_amount: 105.40)
INSERT INTO ordered_product (id, quantity, total_price, unit_price, order_id, product_id, note)
VALUES (16, 2, 75.00, 37.50, 6, 13, null),
       (17, 1, 22.00, 22.00, 6, 5, null),
       (18, 1, 8.40, 8.40, 6, 4, null);

-- Pedido 7 (total_amount: 120.40)
INSERT INTO ordered_product (id, quantity, total_price, unit_price, order_id, product_id, note)
VALUES (19, 1, 52.00, 52.00, 7, 16, null),
       (20, 1, 32.00, 32.00, 7, 17, null),
       (21, 2, 26.40, 13.20, 7, 4, null);

-- Pedido 8 (total_amount: 135.90)
INSERT INTO ordered_product (id, quantity, total_price, unit_price, order_id, product_id, note)
VALUES (22, 1, 35.90, 35.90, 8, 1, 'Mais pimenta'),
       (23, 1, 52.00, 52.00, 8, 16, null),
       (24, 1, 38.00, 38.00, 8, 11, null);

-- Pedido 9 (total_amount: 110.40)
INSERT INTO ordered_product (id, quantity, total_price, unit_price, order_id, product_id, note)
VALUES (25, 2, 70.00, 35.00, 9, 18, null),
       (26, 2, 30.40, 15.20, 9, 4, null),
       (27, 1, 10.00, 10.00, 9, 5, null);

-- Pedido 10 (total_amount: 123.40)
INSERT INTO ordered_product (id, quantity, total_price, unit_price, order_id, product_id, note)
VALUES (28, 2, 86.00, 43.00, 10, 14, null),
       (29, 1, 28.00, 28.00, 10, 11, null),
       (30, 1, 9.40, 9.40, 10, 4, 'Mais recheio');

UNLOCK TABLES;