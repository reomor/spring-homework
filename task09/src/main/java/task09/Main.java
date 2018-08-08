package task09;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.sql.SQLException;
/*
    Домашнее задание
    Реализовать весь функционал работы с БД в приложении книг с использованием spring-data-jpa репозиториев.
 */
@SpringBootApplication
//@EnableMongoRepositories
public class Main {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(Main.class, args);
    }
}
