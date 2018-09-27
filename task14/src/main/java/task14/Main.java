package task14;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/*
    Домашнее задание
    CRUD приложение с Web UI и хранением данных в БД
    Создайте приложение с хранением сущностей в БД (можно взять DAOs из прошлых занятий)
    Использовать классический View, предусмотреть страницу отображения всех сущностей и создания/редактирования.
    View на Thymeleaf, classic Controllers.

    Переписать приложение с использованием AJAX и REST-контроллеров
    Переписать приложение с классических View на AJAX архитектуру и REST-контроллеры.

    Требуется прикрутить Swagger (с помощью аннотаций) к Вашему веб-приложению.

    В существующее CRUD-приложение добавить мехнизм Form-based аутентификации.
    UsersServices реализовать самостоятельно.

    Требуется протестировать Ваше приложение в стиле BDD с помощью Cucumber, а если совсем хотите наворотов, то и с помощью Gherkin.

    Ввести авторизацию на основе URL и/или доменных сущностей.
    Настроить в приложении авторизацию на уровне URL и/или доменных сущностей.
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
