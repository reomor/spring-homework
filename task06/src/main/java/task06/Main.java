package task06;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.sql.SQLException;
/*
    Домашнее задание
    Переписать приложение для хранения книг на ORM
    Использовать JPA, Hibernate только в качестве JPA-провайдера.

    Добавить комментарии к книгам, и высокоуровневые сервисы, оставляющие комментарии к книгам.

    Покрыть DAO тестами используя H2 базу данных и соответствующий H2 Hibernate-диалект
 */
@SpringBootApplication
public class Main {

    public static void main(String[] args) throws SQLException {
        ApplicationContext context = SpringApplication.run(Main.class);
        Console.main(args); // jdbc:h2:mem:testdb
    }
}
