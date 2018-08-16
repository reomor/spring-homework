package task10;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import task10.domain.Author;
import task10.repository.AuthorRepository;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.time.LocalDate;

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

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(Main.class, args);
    }

    @PostConstruct
    public void init() {
        Author author = new Author(null, "Test", "Sername", LocalDate.now(), "biography");
        if (authorRepository.findAll().size() < 5) {
            authorRepository.save(author);
        }
    }
}
