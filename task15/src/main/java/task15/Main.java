package task15;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import task15.nosql.domain.MongoAuthor;
import task15.nosql.domain.MongoBook;
import task15.nosql.domain.MongoComment;
import task15.nosql.domain.MongoGenre;
import task15.nosql.repository.AuthorRepository;
import task15.nosql.repository.BookRepository;
import task15.service.JobService;
import task15.sql.repository.AuthorDao;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

/*
    Домашнее задание
    CRUD приложение с Web UI и хранением данных в БД
    Создайте приложение с хранением сущностей в БД (можно взять DAOs из прошлых занятий)
    Использовать классический View, предусмотреть страницу отображения всех сущностей и создания/редактирования.
    View на Thymeleaf, classic Controllers.

    Разработать процедуру миграции данных из реляционного хранилища в NoSQL или наоборот
    Используя Spring Batch.
    Опционально: Сделать restart с помощью Spring Shell.
 */
@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private JobService jobService;

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(Main.class, args);
    }

    @PostConstruct
    public void init() {
        MongoAuthor mongoAuthor = new MongoAuthor("Name", "Sername", LocalDate.now(), "Biography");
        authorRepository.save(mongoAuthor);
        MongoBook mongoBook = new MongoBook(
                "Title",
                new MongoGenre("Genre", "Genre descsription"),
                "978-3-16-148410-0",
                "Book description",
                Collections.singletonList(mongoAuthor),
                Collections.singletonList(new MongoComment("Comment body", LocalDateTime.now()))
        );
        bookRepository.save(mongoBook);
    }

    @Override
    public void run(String... args) throws Exception {
//        System.out.println(authorRepository.findAll().size());
//        System.out.println(authorDao.getAll().size());
//        Thread.sleep(3000);
        jobService.start("noSqlToSqlMigration");
    }
}
