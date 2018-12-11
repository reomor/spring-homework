package task12;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import task12.domain.UserRoles;
import task12.service.UserService;

import javax.annotation.PostConstruct;

/*
    Домашнее задание
    CRUD приложение с Web UI и хранением данных в БД
    Создайте приложение с хранением сущностей в БД (можно взять DAOs из прошлых занятий)
    Использовать классический View, предусмотреть страницу отображения всех сущностей и создания/редактирования.
    View на Thymeleaf, classic Controllers.

    Переписать приложение с использованием AJAX и REST-контроллеров
    Переписать приложение с классических View на AJAX архитектуру и REST-контроллеры.

    Требуется прикрутить Swagger (с помощью аннотаций) к Вашему веб-приложению.

    Сваггер - очень удобная штука для документирования и тестирования REST API.
    В крутых веб-приложениях иногда просто даются ссылку на сваггер с подробным описанием, и часто этого просто достаточно для интеграции с сервисом.

    Большая просьба - отдокументировать все параметры и не документировать контроллеры ошибок спринг-бут.

    В CRUD Web-приложение добавить механизм аутентификации
    В существующее CRUD-приложение добавить мехнизм Form-based аутентификации.
    UsersServices реализовать самостоятельно.

    Требуется протестировать Ваше приложение в стиле BDD с помощью Cucumber, а если совсем хотите наворотов, то и с помощью Gherkin
    Вот замечательная статья - https://habr.com/post/332754/
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initUsers() {
        try {
            userService.register("User", "usr@a.ru", "123", UserRoles.ROLE_USER);
            userService.register("Admin", "adm@a.ru", "123", UserRoles.ROLE_USER, UserRoles.ROLE_ADMIN);
        } catch (Exception ignore) {
            /* NOP */
        }
    }
}
