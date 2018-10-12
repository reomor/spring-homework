package task17;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
    Домашнее задание
    Реализовать весь функционал работы с БД в приложении книг с использованием spring-data-jpa репозиториев.

    Использовать метрики, healthchecks и logfile к приложению
    И любую другую функциональность на выбор.
    Опционально: переписать приложение на HATEOAS принципах.
 */

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
