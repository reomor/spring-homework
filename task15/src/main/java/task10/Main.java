package task10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.sql.SQLException;

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
public class Main {
    public static void main(String[] args) throws SQLException {
        SpringApplication.run(Main.class, args);
    }
}
