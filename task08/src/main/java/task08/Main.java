package task08;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.sql.SQLException;

/*
    Домашнее задание
    Использовать MongoDB и spring-data для хранения информации о книгах
    Тесты можно реализовать с помощью spring-boot-starter-embedded-mongodb
 */
@SpringBootApplication
public class Main {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(Main.class, args);
    }
}
