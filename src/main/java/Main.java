import java.sql.Connection;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    private static boolean flag = true;
    private static Scanner scanner = new Scanner(System.in);
    private static InteractionWithDB interaction = new InteractionWithDB();

    public static void main(String[] args) throws SQLException {
        try(Connection connection = DataBaseConnection.getConnection()) {
            while (flag) {
                System.out.println("Выберите какой запрос хотите ввести");
                printMenu();
                String input = scanner.nextLine();
                switch (input){
                    case "1": interaction.getAllAuthors(connection); break;
                    case "2": interaction.getAllBooks(connection); break;
                    case "3": interaction.getAllLoans(connection); break;
                    case "4": interaction.getAllReaders(connection); break;
                    case "5": interaction.getAllReviews(connection); break;
                    case "6": getBooksForGenre(connection); break;
                    case "7": getBooksForReaders(connection); break;
                    case "8": getOverdueBooks(connection); break;
                    case "9": addAuthor(connection); break;
                    case "10": addBook(connection); break;
                    case "11": addReader(connection); break;
                    case "12": addLoan(connection); break;
                    case "13": addReview(connection); break;
                    case "14": setStatusReturned(connection); break;
                    case "15": removeReaderById(connection); break;
                    case "16": flag = false; break;
                    default: System.out.println("Invalid input");
                }
            }
        }catch (SQLException e){
            System.out.println("Ошибка подключения" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void printMenu(){
        System.out.println("1 - Вывести всех авторов" +
                "\n2 - Вывести все книги" +
                "\n3 - Вывести все сборы книг" +
                "\n4 - Вывести всех читателей" +
                "\n5 - Вывести все отзывы" +
                "\n6 - Топ книг указанного жанра" +
                "\n7 - Все книги, которые сейчас на руках у указанного читателя" +
                "\n8 - Просроченные выдачи" +
                "\n9 - Добавить нового автора" +
                "\n10 - Добавить новую книгу" +
                "\n11 - Добавить нового читателя" +
                "\n12 - Оформить новую выдачу книги" +
                "\n13 - Добавить отзыв к книге" +
                "\n14 - Отметить возврат книги" +
                "\n15 - Удалить читателя по id" +
                "\n16 - Выйти");
    }

    public static void getBooksForGenre(Connection connection) throws SQLException {
        System.out.println("Введите жанр книг");
        String genreForTop = scanner.nextLine();
        interaction.getBooksForGenre(connection, genreForTop);
    }

    public static void getBooksForReaders(Connection connection) throws SQLException{
        System.out.println("Введите имя читателя");
        String nameReaderForBook = scanner.nextLine();
        interaction.getBooksForReaders(connection,nameReaderForBook);
    }

    public static void getOverdueBooks(Connection connection) throws SQLException{
        System.out.println("Введите количество дней, после которых книга уже будет просрочена");
        int dayForOverdue = Integer.parseInt(scanner.nextLine());
        interaction.getOverdueBooks(connection,dayForOverdue);
    }

    public static void addAuthor(Connection connection) throws SQLException{
        System.out.println("Введите имя автора");
        String nameAuthor = scanner.nextLine();
        System.out.println("Введите страну автора");
        String country = scanner.nextLine();
        System.out.println("Введите год рождения автора");
        int birthYear = Integer.parseInt(scanner.nextLine());
        interaction.addAuthor(connection, nameAuthor, country, birthYear);
    }

    public static void addBook(Connection connection) throws SQLException{
        System.out.println("Введите имя книги");
        String title = scanner.nextLine();
        interaction.getAllAuthors(connection);
        System.out.println("Введите id автора");
        int authorId = Integer.parseInt(scanner.nextLine());
        System.out.println("Введите жанр книги");
        String genre = scanner.nextLine();
        System.out.println("Введите количество страниц книги");
        int pages = Integer.parseInt(scanner.nextLine());
        System.out.println("Введите isbn книги");
        String isbn = scanner.nextLine();
        interaction.addBook(connection, title, authorId, genre, pages, isbn);
    }

    public static void addReader(Connection connection) throws SQLException{
        System.out.println("Введите имя читателя");
        String nameReader = scanner.nextLine();
        System.out.println("Введите email читателя");
        String email = scanner.nextLine();
        interaction.addReader(connection, nameReader, email);
    }

    public static void addLoan(Connection connection) throws SQLException{
        System.out.println("Выберите id книги");
        interaction.getAllBooks(connection);
        int bookIdForLoan = Integer.parseInt(scanner.nextLine());
        System.out.println("Выберите id читателя");
        interaction.getAllReaders(connection);
        int readerIdForLoan = Integer.parseInt(scanner.nextLine());
        interaction.addLoan(connection,bookIdForLoan,readerIdForLoan,null,"выдана");
    }

    public static void addReview(Connection connection) throws SQLException{
        System.out.println("Выберите id книги");
        interaction.getAllBooks(connection);
        int bookIdForReview = Integer.parseInt(scanner.nextLine());
        System.out.println("Выберите id читателя");
        interaction.getAllReaders(connection);
        int readerIdForReview = Integer.parseInt(scanner.nextLine());
        System.out.println("Введите рейтинг книги");
        int rating = Integer.parseInt(scanner.nextLine());
        System.out.println("Введите комментарий к книге");
        String comment = scanner.nextLine();
        interaction.addReview(connection,bookIdForReview, readerIdForReview, rating, comment);
    }

    public static void setStatusReturned(Connection connection) throws SQLException{
        System.out.println("Выберите id записи");
        interaction.getAllLoans(connection);
        int loanId = Integer.parseInt(scanner.nextLine());
        interaction.setStatusReturned(connection,loanId,"возвращена");
    }

    public static void removeReaderById(Connection connection) throws SQLException{
        System.out.println("Выберите id читателя");
        interaction.getAllReaders(connection);
        int readerIdForDelete = Integer.parseInt(scanner.nextLine());
        try {
            interaction.removeReaderByID(connection,readerIdForDelete);
            System.out.println("Читатель удален!");
        } catch (SQLException e){
            System.out.println("Не удалось удалить из-за связи с другими сущностями");
        }
    }
}
