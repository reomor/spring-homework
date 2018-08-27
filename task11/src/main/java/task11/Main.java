package task11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.sql.SQLException;

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
 */
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
