package task12;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.sql.SQLException;

/*
    Домашнее задание
    Использовать WebFlux
    Вместо классического потока и embedded Web-сервера использовать WebFlux.
 */
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
