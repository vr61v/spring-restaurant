insert into "user"
    (id, name, phone, email, date_of_birth, gender, role) values
    ('43956d69-ce2b-463a-87ca-00fbc6eac42d', 'get', '+79000000000', 'something@mail.ru', '01/01/2000', 'MALE', 'USER'),
    ('ea3d1a8f-68c5-4fbd-85e6-5621f63ea58e', 'put', '+79000000000', 'something@mail.ru', '01/01/2000', 'MALE', 'USER'),
    ('adfe40f3-99a5-455a-8d4a-dba16f35b709', 'delete', '+79000000000', 'something@mail.ru', '01/01/2000', 'MALE', 'USER');


insert into "restaurant" values ('5a48a55c-41f4-41e0-8317-144575508ebe', 'orders', 'orders', '09:00:00', '23:00:00');
insert into "user" values ('3182bd64-3181-4610-8505-23ed6fa21da2', 'orders', '+79000000000', 'something@mail.ru', '01/01/2000', 'MALE', 'USER');
insert into "order"
    (id, user_id, restaurant_id, date, comment, state) values
    ('75420544-f0b3-453f-8cbb-0bdee2875b65', '3182bd64-3181-4610-8505-23ed6fa21da2', '5a48a55c-41f4-41e0-8317-144575508ebe', now(), 'get', 'CREATE'),
    ('2835feb6-dbd9-4d43-ad1b-48f0bbc2ef7c', '3182bd64-3181-4610-8505-23ed6fa21da2', '5a48a55c-41f4-41e0-8317-144575508ebe', now(), 'pay', 'CREATE'),
    ('a778ebae-3800-4625-a929-a4bdef4a5be2', '3182bd64-3181-4610-8505-23ed6fa21da2', '5a48a55c-41f4-41e0-8317-144575508ebe', now(), 'cancel', 'CREATE');


insert into "user" values ('265c71c7-1cbc-4d51-a290-9ef2eede8fee', 'cards', '+79000000000', 'something@mail.ru', '01/01/2000', 'MALE', 'USER');
insert into "card"
    (id, user_id, number, type, discount) values
    ('2940efcc-f385-47a2-9f16-6cd385db16c8', '265c71c7-1cbc-4d51-a290-9ef2eede8fee', 'get', 'GOLD', 0.10),
    ('6c122676-26d1-431a-9e8a-077548c5f0fd', '265c71c7-1cbc-4d51-a290-9ef2eede8fee', 'delete', 'GOLD', 0.10),
    ('79ecc26e-3d5b-44b3-91de-09ccaf06e8dc', null, 'register', 'GOLD', 0.10);
