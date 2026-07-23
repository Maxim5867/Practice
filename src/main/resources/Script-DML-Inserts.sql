insert into authors(full_name, country, birth_year) values
('William Shakespeare', 'Engalnd', 1564),
('Jane Austen', 'England', 1775),
('Walter Scott', 'England', 1771),
('Alexandre Dumas', 'France', 1802),
('Erich Maria Remarque', 'Germany', 1898),
('Felix Lope de Vega', 'Spain', 1562);

insert into books(title, author_id, genre, pages, isbn) values
('Hamlet', 1, 'трагедия', 342, '978-0-486-27278-8'),
('Romeo and Juliet', 1, 'трагедия', 288, '978-0-7434-7712-3'),
('Pride and Prejudice', 2, 'роман', 432, '978-0-14-143951-8'),
('Emma', 2, 'роман', 474, '978-0-14-143960-0'),
('Ivanhoe', 3, 'исторический роман', 556, '978-0-19-953699-3'),
('The Three Musketeers', 4, 'приключения', 700, '978-0-19-953781-5'),
('The Count of Monte Cristo', 4, 'приключения', 1276, '978-0-14-044926-2'),
('All Quiet on the Western Front', 5, 'военная проза', 296, '978-0-449-91397-7'),
('Three Comrades', 5, 'военная проза', 496, '978-0-449-91229-1'),
('Fuenteovejuna', 6, 'драма', 128, '978-84-376-0295-7');

insert into readers (name, email, registration_date) values
 ('Иван Петров', 'ivan.petrov@mail.ru', '2025-01-15'),
 ('Анна Смирнова', 'anna.smirnova@gmail.com', '2025-03-22'),
 ('Дмитрий Кузнецов', 'dmitry.kuznetsov@yandex.ru', '2025-06-10'),
 ('Ольга Соколова', 'olga.sokolova@mail.ru', '2025-09-05'),
 ('Сергей Волков', 'sergey.volkov@gmail.com', '2026-02-18');

insert into loans(book_id, reader_id, loan_date, return_date, status) values
(1, 1, '2026-06-01', '2026-06-15', 'возвращена'),
(3, 2, '2026-06-05', '2026-06-20', 'возвращена'),
(5, 3, '2026-07-01', NULL, 'выдана'),
(7, 1, '2026-06-10', NULL, 'просрочена'),
(2, 4, '2026-07-15', NULL, 'выдана'),
(9, 5, '2026-05-20', '2026-06-01', 'возвращена'),
(4, 2, '2026-07-05', NULL, 'выдана'),
(10, 3, '2026-06-25', '2026-07-10', 'возвращена');

INSERT INTO reviews (book_id, reader_id, rating, comment) VALUES
(1, 1, 5, 'Гениальная трагедия, перечитываю каждый год'),
(3, 2, 4, 'Очень тонкий психологизм, но темп местами медленный'),
(7, 3, 5, 'Лучшая приключенческая книга, которую я читал'),
(9, 1, 4, 'Сильное антивоенное произведение'),
(5, 4, 3, 'Интересно, но затянуто'),
(2, 5, 5, 'Шекспир вне времени'),
(10, 2, 4, 'Компактно и по делу'),
(4, 3, 5, 'Остроумно и легко читается');