package task08;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.sql.SQLException;
/*
    Домашнее задание
    Реализовать весь функционал работы с БД в приложении книг с использованием spring-data-jpa репозиториев.

    Использовать метрики, healthchecks и logfile к приложению
    И любую другую функциональность на выбор.
    Опционально: переписать приложение на HATEOAS принципах.
 */
@SpringBootApplication
public class Main {

    public static void main(String[] args) throws SQLException {
        ApplicationContext context = SpringApplication.run(Main.class);
        Console.main(args); // jdbc:h2:mem:testdb
    }
}
