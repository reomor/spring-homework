package task09;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import task09.domain.Author;
import task09.domain.Book;
import task09.domain.Comment;
import task09.domain.Genre;
import task09.repository.AuthorRepository;
import task09.repository.BookRepository;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/*
    Домашнее задание
    Использовать MongoDB и spring-data для хранения информации о книгах
    Тесты можно реализовать с помощью spring-boot-starter-embedded-mongodb
 */
@SpringBootApplication
//@EnableMongoRepositories
public class Main {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(Main.class, args);
    }

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @PostConstruct
    public void setUp() {
        Author author = new Author("Name", "Sername", LocalDate.now(), "Biography");
        authorRepository.save(author);
        List<Author> all = authorRepository.findAll();
        System.out.println(all);

        Genre genre = new Genre("Genre", "Genre");
        Comment comment = new Comment("Like a shit", LocalDateTime.now());
        Book book = new Book("Title", genre, "ISBN-01", "Description", Collections.singletonList(author), Collections.singletonList(comment) );
        bookRepository.save(book);

        List<Book> all1 = bookRepository.findAll();
        System.out.println(all1);
    }
}
