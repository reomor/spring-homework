package task10;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import task10.domain.Author;
import task10.domain.Book;
import task10.domain.Comment;
import task10.domain.Genre;
import task10.repository.AuthorRepository;
import task10.repository.BookRepository;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

/*
    Домашнее задание
    CRUD приложение с Web UI и хранением данных в БД
    Создайте приложение с хранением сущностей в БД (можно взять DAOs из прошлых занятий)
    Использовать классический View, предусмотреть страницу отображения всех сущностей и создания/редактирования.
    View на Thymeleaf, classic Controllers.
 */
@SpringBootApplication
public class Main {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(Main.class, args);
    }

    @PostConstruct
    public void init() {
        Author author = new Author(null, "Fridrih", "Engels", LocalDate.now(), "F.E. biography");
        authorRepository.save(author);
        Author author2 = new Author(null, "Karl", "Marks", LocalDate.now(), "K.M. biography");
        authorRepository.save(author2);

        Genre genre = new Genre("Detective", "Detective genre description");
        Comment comment = new Comment("This book is like a shit", LocalDateTime.now());
        Comment comment2 = new Comment("This book is a masterpiece", LocalDateTime.now());
        Book book = new Book(null,"Book about everything", genre, "978-3-16-148410-0", "Description", Arrays.asList(author, author2), Arrays.asList(comment, comment2));
        if (bookRepository.findAll().size() < 5) {
            bookRepository.save(book);
        }
    }
}
