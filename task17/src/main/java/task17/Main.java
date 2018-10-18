package task17;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/*
    Домашнее задание
    Реализовать весь функционал работы с БД в приложении книг с использованием spring-data-jpa репозиториев.

    Использовать метрики, healthchecks и logfile к приложению
    И любую другую функциональность на выбор.
    Опционально: переписать приложение на HATEOAS принципах.

    Обернуть приложение в docker-контейнер
    Обернуть приложение в docker-контейнер, БД тоже. Настроить связь между ними.
    Опционально: сделать это в локальном кубе.
 */
@SpringBootApplication
@EnableWebMvc
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
