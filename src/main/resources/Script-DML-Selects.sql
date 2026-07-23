select * from authors;
select * from books;
select * from loans;
select * from readers;
select * from reviews;

SELECT full_name FROM authors;
SELECT id, full_name FROM authors;
SELECT * FROM authors ORDER BY full_name; -- ASC
SELECT * FROM authors ORDER BY full_name DESC;
SELECT * FROM authors WHERE full_name='William';
SELECT * FROM authors WHERE full_name='William' OR full_name='Jane Austen';
SELECT * FROM authors WHERE full_name LIKE 'Jane';
SELECT * FROM authors WHERE full_name LIKE 'Jane%';

-- Запросы по книгам
SELECT * FROM books;
SELECT COUNT(*) AS "Всего книг" , SUM(pages) AS "Всего страниц" FROM books;
SELECT pages, COUNT(*) FROM books GROUP BY pages ORDER BY pages;

-- Собирательные данные
select a.full_name, b.title, b.genre, round(avg(r.rating),2) as avg_rating from books b join authors a on b.author_id  = a.id join reviews r on r.book_id = b.book_id where b.genre = 'трагедия' group by a.full_name, b.title, b.genre order by avg_rating desc limit 5;
select r.name, b.title, l.loan_date from loans l join books b on l.book_id = b.book_id join readers r on l.reader_id = r.reader_id where r.name = 'Иван Петров' order by l.loan_date;  -- показать все книги, которые сейчас на руках у конкретного читателя
select r.name, a.full_name, b.title, l.loan_date, current_date - l.loan_date as day_passed from loans l join books b on l.book_id = b.book_id join authors a on b.author_id = a.id join readers r on l.reader_id = r.reader_id  where l.return_date is null and current_date - l.loan_date > 14 order by day_passed desc limit 10;