package task13;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import task13.domain.UserRoles;
import task13.service.UserService;

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
 */
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
            userService.create("User", "usr@a.ru", "123", UserRoles.ROLE_USER);
            userService.create("Admin", "adm@a.ru", "123", UserRoles.ROLE_USER, UserRoles.ROLE_ADMIN);
        } catch (Exception ignore) {
            /* NOP */
        }
    }
}
