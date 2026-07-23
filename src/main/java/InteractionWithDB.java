import java.sql.*;
import java.time.LocalDate;

public class InteractionWithDB {
    public void getAllAuthors(Connection connection) throws SQLException {
        if (connection == null || connection.isClosed()) return;
        String column0 = "id", column1 = "full_name", column2 = "country", column3 = "birth_year";

        int param0, param3;
        String param1,param2;

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM authors");

        while (rs.next()){
            param0 = rs.getInt(column0);
            param1 = rs.getString(column1);
            param2 = rs.getString(column2);
            param3 = rs.getInt(column3);
            System.out.println(param0 + " | " + param1 + " | " + param2 + " | " + param3);
        }

        statement.close();
        System.out.println();
    }
    public void getAllBooks(Connection connection) throws SQLException {
        if (connection == null || connection.isClosed()) return;
        String column0 = "book_id", column1 = "title", column2 = "author_id", column3 = "genre", column4 = "pages", column5 = "isbn";

        int param0, param2,param4;
        String param1,param3,param5;

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM books");

        while (rs.next()){
            param0 = rs.getInt(column0);
            param1 = rs.getString(column1);
            param2 = rs.getInt(column2);
            param3 = rs.getString(column3);
            param4 = rs.getInt(column4);
            param5 = rs.getString(column5);
            System.out.println(param0 + " | " + param1 + " | " + param2 + " | " + param3 + " | " + param4 + " | " + param5);
        }

        statement.close();
        System.out.println();
    }
    public void getAllReaders(Connection connection) throws SQLException {
        if (connection == null || connection.isClosed()) return;
        String column0 = "reader_id", column1 = "name", column2 = "email", column3 = "registration_date";

        int param0;
        String param1,param2, param3;

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM readers");

        while (rs.next()){
            param0 = rs.getInt(column0);
            param1 = rs.getString(column1);
            param2 = rs.getString(column2);
            param3 = rs.getString(column3);
            System.out.println(param0 + " | " + param1 + " | " + param2 + " | " + param3);
        }

        statement.close();
        System.out.println();
    }
    public void getAllLoans(Connection connection) throws SQLException {
        if (connection == null || connection.isClosed()) return;
        String column0 = "loan_id", column1 = "book_id", column2 = "reader_id", column3 = "loan_date", column4 = "return_date", column5 = "status";

        int param0, param1,param2;
        String param3,param4,param5;

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM loans");

        while (rs.next()){
            param0 = rs.getInt(column0);
            param1 = rs.getInt(column1);
            param2 = rs.getInt(column2);
            param3 = rs.getString(column3);
            param4 = rs.getString(column4);
            param5 = rs.getString(column5);
            System.out.println(param0 + " | " + param1 + " | " + param2 + " | " + param3 + " | " + param4 + " | " + param5);
        }

        statement.close();
        System.out.println();
    }
    public void getAllReviews(Connection connection) throws SQLException {
        if (connection == null || connection.isClosed()) return;
        String column0 = "review_id", column1 = "book_id", column2 = "reader_id", column3 = "rating", column4 = "comment";

        int param0, param1, param2,param3;
        String param4;

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM reviews");

        while (rs.next()){
            param0 = rs.getInt(column0);
            param1 = rs.getInt(column1);
            param2 = rs.getInt(column2);
            param3 = rs.getInt(column3);
            param4 = rs.getString(column4);
            System.out.println(param0 + " | " + param1 + " | " + param2 + " | " + param3 + " | " + param4);
        }

        statement.close();
        System.out.println();
    }

    public void getBooksForGenre(Connection connection, String genre) throws SQLException {
        if (connection == null || connection.isClosed()) return;
        if (genre == null || genre.isBlank()) return;

        String param;

        PreparedStatement statement = connection.prepareStatement("select a.full_name, b.title, b.genre, round(avg(r.rating),2) as avg_rating from books b join authors a on b.author_id  = a.id join reviews r on r.book_id = b.book_id where b.genre = ? group by a.full_name, b.title, b.genre order by avg_rating desc limit 5");
        statement.setString(1, genre);
        ResultSet rs = statement.executeQuery();
        int count = rs.getMetaData().getColumnCount();

        while (rs.next()){
            param = "";
            for (int i = 1; i <= count; i++) {
                param += rs.getString(i);
                if (i != count) param += " | ";
            }
            System.out.println(param);
        }

        statement.close();
        System.out.println();
    }

    public void getBooksForReaders(Connection connection, String name) throws SQLException {
        if (connection == null || connection.isClosed()) return;
        if (name == null || name.isBlank()) return;

        String param;
        name = '%' + name + '%';

        PreparedStatement statement = connection.prepareStatement("select r.name, b.title, l.loan_date from loans l join books b on l.book_id = b.book_id join readers r on l.reader_id = r.reader_id where r.name LIKE ? order by l.loan_date");
        statement.setString(1,name);
        ResultSet rs = statement.executeQuery();
        int count = rs.getMetaData().getColumnCount();

        while (rs.next()){
            param = "";
            for (int i = 1; i <= count; i++) {
                param += rs.getString(i);
                if (i != count) param += " | ";
            }
            System.out.println(param);
        }

        statement.close();
        System.out.println();
    }

    public void getOverdueBooks(Connection connection, int dayForOverdue) throws SQLException {
        if (connection == null || connection.isClosed()) return;
        if (dayForOverdue == 0) return;

        String param;

        PreparedStatement statement = connection.prepareStatement("select r.name, a.full_name, b.title, l.loan_date, current_date - l.loan_date as day_passed from loans l join books b on l.book_id = b.book_id join authors a on b.author_id = a.id join readers r on l.reader_id = r.reader_id  where l.return_date is null and current_date - l.loan_date > ? order by day_passed desc limit 10");
        statement.setInt(1,dayForOverdue);
        ResultSet rs = statement.executeQuery();
        int count = rs.getMetaData().getColumnCount();

        while (rs.next()){
            param = "";
            for (int i = 1; i <= count; i++) {
                param += rs.getString(i);
                if (i != count) param += " | ";
            }
            System.out.println(param);
        }

        statement.close();
        System.out.println();
    }

    public void addAuthor(Connection connection, String name, String country, int birthYear) throws SQLException {
        if (connection == null || connection.isClosed()) return;
        if (name == null || name.isBlank() || country == null || country.isBlank()) return;
        if (birthYear == 0) return;

        PreparedStatement statement = connection.prepareStatement("INSERT INTO authors(full_name, country, birth_year) VALUES (?, ?, ?)");
        statement.setString(1,name);
        statement.setString(2,country);
        statement.setInt(3,birthYear);

        statement.executeUpdate();

        statement.close();
        getAllAuthors(connection);
    }

    public void addBook(Connection connection, String title, int authorId, String genre, int pages, String isbn) throws SQLException {
        if (connection == null || connection.isClosed()) return;
        if (title == null || title.isBlank() || genre == null || genre.isBlank() || isbn == null || isbn.isBlank()) return;
        if (!checkAuthorId(connection,authorId) || pages == 0) return;

        PreparedStatement statement = connection.prepareStatement("INSERT INTO books(title, author_id, genre, pages, isbn) VALUES (?,?,?,?,?)");
        statement.setString(1,title);
        statement.setInt(2,authorId);
        statement.setString(3,genre);
        statement.setInt(4,pages);
        statement.setString(5,isbn);

        statement.executeUpdate();

        statement.close();
        getAllBooks(connection);
    }

    public void addReader(Connection connection, String name, String email) throws SQLException {
        if (connection == null || connection.isClosed()) return;
        if (name == null || name.isBlank() || email == null || email.isBlank()) return;

        PreparedStatement statement = connection.prepareStatement("INSERT INTO readers(name,email) VALUES (?,?)");
        statement.setString(1,name);
        statement.setString(2,email);

        statement.executeUpdate();

        statement.close();
        getAllReaders(connection);
    }

    public void addLoan(Connection connection,int bookId, int readerId, String returnDate, String status) throws SQLException{
        if (connection == null || connection.isClosed()) return;
        if (!checkBookId(connection, bookId) || !checkReaderId(connection,readerId) || status == null || status.isBlank()) return;

        PreparedStatement statement = connection.prepareStatement("INSERT INTO loans(book_id, reader_id, return_date, status) VALUES (?,?,?,?)");

        statement.setInt(1,bookId);
        statement.setInt(2,readerId);
        if (returnDate == null || returnDate.isEmpty()) {
            statement.setNull(3, java.sql.Types.DATE);
        }else {
            statement.setDate(3, java.sql.Date.valueOf(returnDate));
        }
        statement.setString(4,status);

        statement.executeUpdate();

        statement.close();
        getAllLoans(connection);
    }

    public void addReview(Connection connection, int bookId, int readerId, int rating, String comment) throws SQLException{
        if (connection == null || connection.isClosed()) return;
        if (!checkBookId(connection, bookId) || !checkReaderId(connection,readerId) || rating < 1 || rating > 5) return;

        PreparedStatement statement = connection.prepareStatement("INSERT INTO reviews(book_id, reader_id, rating, comment) VALUES (?,?,?,?)");

        statement.setInt(1,bookId);
        statement.setInt(2, readerId);
        statement.setInt(3, rating);
        statement.setString(4,comment);

        statement.executeUpdate();

        statement.close();
        System.out.println();
    }

    public void setStatusReturned(Connection connection, int loanId, String status) throws SQLException{
        if (connection == null || connection.isClosed()) return;
        if (!checkLoanId(connection,loanId)) return;

        PreparedStatement statement = connection.prepareStatement("UPDATE loans SET status = ?, return_date = ? WHERE loan_id = ?");
        statement.setString(1,status);
        statement.setDate(2, Date.valueOf(LocalDate.now()));
        statement.setInt(3,loanId);

        statement.executeUpdate();

        statement.close();
        System.out.println();
    }

    public void removeReaderByID(Connection connection, int readerID) throws SQLException {
        if (connection == null || connection.isClosed()) return;
        if (!checkReaderId(connection,readerID)) return;

        PreparedStatement statement = connection.prepareStatement("DELETE FROM readers WHERE reader_id = ?");
        statement.setInt(1,readerID);

        statement.executeUpdate();

        statement.close();
        getAllReaders(connection);
    }

    public boolean checkAuthorId(Connection connection, int authorId) throws SQLException{
        try(PreparedStatement statement = connection.prepareStatement("SELECT id FROM authors WHERE id = ?")){
            statement.setInt(1,authorId);
            try(ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }
    public boolean checkBookId(Connection connection, int bookId) throws SQLException{
        try(PreparedStatement statement = connection.prepareStatement("SELECT book_id FROM books WHERE book_id = ?")){
            statement.setInt(1,bookId);
            try(ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }
    public boolean checkReaderId(Connection connection, int readerID) throws SQLException{
        try(PreparedStatement statement = connection.prepareStatement("SELECT reader_id FROM readers WHERE reader_id = ?")){
            statement.setInt(1,readerID);
            try(ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }
    public boolean checkLoanId(Connection connection, int loanId) throws SQLException{
        try(PreparedStatement statement = connection.prepareStatement("SELECT loan_id FROM loans WHERE loan_id = ?")){
            statement.setInt(1,loanId);
            try(ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }
}
