-- UserController tests data
insert into "user"
    (id, name, phone, email, date_of_birth, gender, role) values
    ('43956d69-ce2b-463a-87ca-00fbc6eac42d', 'get', '+79000000000', 'something@mail.ru', '01/01/2000', 'MALE', 'USER'),
    ('ea3d1a8f-68c5-4fbd-85e6-5621f63ea58e', 'put', '+79000000000', 'something@mail.ru', '01/01/2000', 'MALE', 'USER'),
    ('adfe40f3-99a5-455a-8d4a-dba16f35b709', 'delete', '+79000000000', 'something@mail.ru', '01/01/2000', 'MALE', 'USER');

insert into "restaurant" values ('5a48a55c-41f4-41e0-8317-144575508ebe', 'UserRestaurantTestsMock', '+79000000000', '09:00:00', '23:00:00');
insert into "user" values ('3182bd64-3181-4610-8505-23ed6fa21da2', 'UserUserTestsMock', '+79000000000', 'something@mail.ru', '01/01/2000', 'MALE', 'USER');
insert into "order"
    (id, user_id, restaurant_id, date, comment, state) values
    ('75420544-f0b3-453f-8cbb-0bdee2875b65', '3182bd64-3181-4610-8505-23ed6fa21da2', '5a48a55c-41f4-41e0-8317-144575508ebe', now(), 'get', 'CREATE'),
    ('2835feb6-dbd9-4d43-ad1b-48f0bbc2ef7c', '3182bd64-3181-4610-8505-23ed6fa21da2', '5a48a55c-41f4-41e0-8317-144575508ebe', now(), 'pay', 'CREATE'),
    ('a778ebae-3800-4625-a929-a4bdef4a5be2', '3182bd64-3181-4610-8505-23ed6fa21da2', '5a48a55c-41f4-41e0-8317-144575508ebe', now(), 'cancel', 'CREATE');


insert into "user" values ('265c71c7-1cbc-4d51-a290-9ef2eede8fee', 'UserCardsTestsMock', '+79000000000', 'something@mail.ru', '01/01/2000', 'MALE', 'USER');
insert into "card"
    (id, user_id, number, type, discount) values
    ('2940efcc-f385-47a2-9f16-6cd385db16c8', '265c71c7-1cbc-4d51-a290-9ef2eede8fee', 'get', 'GOLD', 0.10),
    ('6c122676-26d1-431a-9e8a-077548c5f0fd', '265c71c7-1cbc-4d51-a290-9ef2eede8fee', 'delete', 'GOLD', 0.10),
    ('79ecc26e-3d5b-44b3-91de-09ccaf06e8dc', null, 'register', 'GOLD', 0.10);


-- CookController tests data
insert into "restaurant" values ('073a765d-76c0-446d-9210-968a009d440e', 'CookRestaurantTestsMock', '+79000000000', '09:00:00', '23:00:00');
insert into "restaurant" values ('fae6b4ea-8056-41fb-876d-ab960c79b5b8', 'CookRestaurantTestsMock', '+79000000000', '09:00:00', '23:00:00');
insert into "user" values ('58e1da2f-b3c2-4d00-a5da-5b80a635a7ab', 'CookUserTestsMock', '+79000000000', 'something@mail.ru', '01/01/2000', 'MALE', 'USER');
insert into "order"
    (id, user_id, restaurant_id, date, comment, state) values
    ('34bf11a1-988b-47e7-9f07-1b8e6508fc65', '58e1da2f-b3c2-4d00-a5da-5b80a635a7ab', 'fae6b4ea-8056-41fb-876d-ab960c79b5b8', now(), 'get', 'PAY'),
    ('53a9a6ce-a27f-4676-9e03-ec631b718590', '58e1da2f-b3c2-4d00-a5da-5b80a635a7ab', 'fae6b4ea-8056-41fb-876d-ab960c79b5b8', now(), 'get', 'PAY'),
    ('80718701-3718-4523-9af8-ab5b783f74af', '58e1da2f-b3c2-4d00-a5da-5b80a635a7ab', 'fae6b4ea-8056-41fb-876d-ab960c79b5b8', now(), 'get', 'COOK'),
    ('c33d5ed8-f587-4b6c-8b76-5f7bd85186fe', '58e1da2f-b3c2-4d00-a5da-5b80a635a7ab', 'fae6b4ea-8056-41fb-876d-ab960c79b5b8', now(), 'get', 'COOK'),
    ('cce88ac5-7c4f-4634-88c3-67f846065e84', '58e1da2f-b3c2-4d00-a5da-5b80a635a7ab', '073a765d-76c0-446d-9210-968a009d440e', now(), 'take', 'PAY'),
    ('79078466-b01c-4817-a309-c3f934dfbd6d', '58e1da2f-b3c2-4d00-a5da-5b80a635a7ab', '073a765d-76c0-446d-9210-968a009d440e', now(), 'complete', 'COOK'),
    ('6dd4c3f6-546b-495b-9809-9fffeb98f536', '58e1da2f-b3c2-4d00-a5da-5b80a635a7ab', '073a765d-76c0-446d-9210-968a009d440e', now(), 'cancel', 'COOK');


-- AdminController tests data
insert into "user"
    (id, name, phone, email, date_of_birth, gender, role) values
    ('60c0f10c-4ee6-4682-8566-1b4a1d131afc', 'get', '+79000000000', 'something@mail.ru', '01/01/2000', 'MALE', 'ADMIN'),
    ('7a7cfa5b-1ea2-4c74-9e6d-aeddcb5492de', 'put', '+79000000000', 'something@mail.ru', '01/01/2000', 'MALE', 'ADMIN'),
    ('5d355992-ed99-4644-b5a8-756d2fa7edef', 'delete', '+79000000000', 'something@mail.ru', '01/01/2000', 'MALE', 'ADMIN');

insert into "restaurant"
    (id, address, phone, opening_hours_from, opening_hours_to) values
    ('e36d91e5-5277-41cb-8375-94c645c2ffef', 'get', '+79000000000', '09:00:00', '23:00:00'),
    ('99281df3-e6a5-45dc-87bc-ce6189595a8d', 'put', '+79000000000', '09:00:00', '23:00:00'),
    ('ac082680-3818-4915-88ba-8ffebdee3d7c', 'delete', '+79000000000', '09:00:00', '23:00:00');

insert into "category"
    (id, name) values
    ('85688b63-da43-4330-89ae-08a4c8c98577', 'get'),
    ('e5199c15-c828-4bab-9816-2e0e6c79320e', 'put'),
    ('1a9b6b02-4fd3-4b93-b928-aedd067d5d07', 'delete');


insert into "category" values ('d77c53c9-c3c9-41d7-8c58-a436df7c7bc8', 'AdminProductTestsMock'),
                              ('dc854133-de51-441e-b6c0-b1f9b44201d6', 'AdminProductPutTestsMock');
insert into "product"
    (id, category_id, name, price, weight, composition, description) values
    ('1cba6efc-654a-4f6c-b4c2-0390e56b1553', 'd77c53c9-c3c9-41d7-8c58-a436df7c7bc8', 'get', 1, 1, 'something...', 'something...'),
    ('227f7256-c35e-41a3-bbd3-f46795a369b6', 'd77c53c9-c3c9-41d7-8c58-a436df7c7bc8', 'put', 1, 1, 'something...', 'something...'),
    ('c3cee70c-7f83-48ea-85de-783b22482c36', 'd77c53c9-c3c9-41d7-8c58-a436df7c7bc8', 'delete', 1, 1, 'something...', 'something...')
