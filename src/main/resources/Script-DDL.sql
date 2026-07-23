drop table if exists authors,books,readers,loans,reviews cascade;

create table authors(
    id int primary key generated always as identity,
    full_name VARCHAR(100) not null,
    country varchar(50),
    birth_year int not null,

    CONSTRAINT authors_check_country_not_empty check (country is not null and trim(country) <> '')
);

create table books(
    book_id int primary key generated always as identity,
    title VARCHAR(255) not null,
    author_id int references authors(id),
    genre varchar(50),
    pages int,
    isbn varchar(100) unique,

    CONSTRAINT books_check_genre_not_empty check (genre is not null and trim(genre) <> ''),
    CONSTRAINT books_check_isbn_not_empty check (isbn is not null and trim(isbn) <> ''),
    CONSTRAINT books_check_pages_not_empty check (pages > 0)
);

create table readers(
    reader_id int primary key generated always as identity,
    name varchar(100) not null,
    email varchar(100) unique,
    registration_date date default current_date,

    CONSTRAINT readers_check_email_not_empty check (email is not null and trim(email) <> '')
);

create table loans(
    loan_id int primary key generated always as identity,
    book_id int references books(book_id),
    reader_id int references readers(reader_id),
    loan_date date default current_date,
    return_date date,
    status VARCHAR(20) not null,

    constraint loans_check_status_in check (status in ('выдана', 'возвращена', 'просрочена'))
);

create table reviews(
    review_id int primary key generated always as identity,
    book_id int references books(book_id),
    reader_id int references readers(reader_id),
    rating int not null,
    comment varchar(255),

    constraint reviews_check_rating_between check (rating between 1 and 5)
);