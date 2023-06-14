DELETE FROM user_role WHERE true;
DELETE FROM users WHERE true;
DELETE FROM meals WHERE true;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, dateTime, description, calories)
VALUES (100000, '2020-01-30T10:00', 'Завтрак', 500),
       (100000, '2020-01-30T13:00', 'Обед', 1000),
       (100000, '2020-01-30T20:00', 'Ужин', 500),
       (100001, '2020-01-31T00:00', 'Еда на граничное значение', 100),
       (100001, '2020-01-31T10:00', 'Завтрак', 1000),
       (100001, '2020-01-31T13:00', 'Обед', 500),
       (100001, '2020-01-31T20:00', 'Ужин', 410);