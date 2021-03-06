# [Разработчик на Spring Framework](https://otus.ru/lessons/javaspring/)

Домашние задания по курсу экосистемы Spring Framework

[Mentor's code review](https://github.com/reomor/spring-homework/pulls)

---
#### Task 01
#### [Программа по проведению тестирования](https://github.com/reomor/spring-homework/tree/dev/task01)
В ресурсах хранятся вопросы и различные ответы к ним в виде CSV файла (5 вопросов). 
Программа должна спросить у пользователя фамилию и имя, спросить 5 вопросов из CSV-файла и вывести результат тестирования. <br>
Ecosystem:
> Spring Context
 
#### Task 02
#### [Annotation- + Java-based конфигурация приложения](https://github.com/reomor/spring-homework/tree/dev/task02)
Переписать конфигурацию в виде Java + Annotation-based конфигурации.
Локализовать выводимые сообщения и вопросы теста. Обернуть вызов одной из функций аспектом. <br>
Ecosystem:
> - Spring Context
> - AspectJ

#### Task 03
#### [YML-настройки и тестирование](https://github.com/reomor/spring-homework/tree/dev/task03)
Добавить файл настроек *.yml. Реализовать тестирование функционала.
Ecosystem:
> - Spring Boot
> - Spring Test

#### Task 04
#### [Spring Shell поддержка](https://github.com/reomor/spring-homework/tree/dev/task04)
Перевести приложение для проведения опросов на Spring Shell. Проект создать через spring-initializer.
Реализовать banner и тесты. Перенести все свойства в application.yml. <br>
Ecosystem:
> - Spring Boot
> - Spring Boot Test
> - Spring Shell
 
#### Task 05
#### [Spring JDBC](https://github.com/reomor/spring-homework/tree/dev/task05)
Создать приложение хранящее информацию о книгах в билиотеке. Использовать Spring JDBC и реляционную базу.
Использовать реляционную БД и встроенную in-memory. Предусмотреть таблицы авторов, книг и жанров. <br>
Ecosystem:
> - Spring Boot
> - Spring Boot Test
> - Spring JDBC
> - Embedded Postgres
> - H2
 
#### Task 06
#### [Spring JPA](https://github.com/reomor/spring-homework/tree/dev/task06)
Переписать приложение для хранения книг с использованием ORM. Использовать JPA, Hibernate только в качестве JPA-провайдера.
Покрыть DAO тестами используя H2 базу данных и соответствующий H2 Hibernate-диалект. <br>
Ecosystem:
> - Spring Boot
> - Spring Boot Test
> - Spring Boot JDBC
> - Spring Data JPA
> - Liquibase
> - Spring Shell
> - H2
 
#### Task 07
#### [Spring Data JPA](https://github.com/reomor/spring-homework/tree/dev/task07)
Реализовать весь функционал работы с БД в приложении для хранения книг с использованием DATA JPA репозиториев. <br>
Ecosystem:
> - Spring Boot
> - Spring Boot Test
> - Spring Data Jpa
> - Spring Shell
> - H2
 
#### Task 08
#### [Spring Data Mongodb](https://github.com/reomor/spring-homework/tree/dev/task08)
Использовать MongoDB и spring-data для хранения информации о книгах.
Тесты реализовать с помощью embedded-mongodb. <br>
Ecosystem:
> - Spring Boot
> - Spring Boot Test
> - Spring Data Mongodb
> - Lombok
> - Embedded Mongodb
> - Spring Shell
 
#### Task 09
#### [Spring Web MVC](https://github.com/reomor/spring-homework/tree/dev/task09)
Разработать CRUD приложение с Web UI и хранением данных в БД.
Использовать классический View, предусмотреть страницу отображения всех сущностей и создания/редактирования.
View на Thymeleaf, classic Controllers. <br>
Ecosystem:
> - Spring Boot
> - Spring Boot Test
> - Spring Data Mongodb
> - Spring Web
> - Thymeleaf
> - Lombok
> - Bootstrap
> - JQuery
> - Embedded Mongodb
  
#### Task 10
#### [Spring Web REST](https://github.com/reomor/spring-homework/tree/dev/task10)
Разработать CRUD приложение с Web UI и хранением данных в БД.
Использовать AJAX и REST-контроллеры. Переписать приложение с классических View на AJAX архитектуру и REST-контроллеры.
Использовать Swagger (с помощью аннотаций). <br>
Ecosystem:
> - Spring Boot
> - Spring Boot Test
> - Swagger 2
> - Spring Data Mongodb
> - Spring Web
> - Thymeleaf
> - Lombok
> - Bootstrap
> - JQuery
> - Embedded Mongodb
 
#### Task 11
#### [Spring WebFlux](https://github.com/reomor/spring-homework/tree/dev/task11)
Использовать WebFlux вместо классического embedded web-server. <br>
Ecosystem:
> - Spring Boot
> - Spring Data Mongodb Reactive
> - Spring WebFlux
> - Lombok
> - Embedded Mongodb
> - Reactor
> - Reactor-Test
 
#### Task 12
#### [Spring Security](https://github.com/reomor/spring-homework/tree/dev/task12)
В CRUD Web-приложение добавить мехнизм Form-based аутентификации.
Дополнительно реализовать UsersServices. Реализовать пример BDD-тестирования. <br>
Ecosystem:
> - Spring Boot
> - Spring Data Mongodb
> - Spring Web
> - Thymeleaf
> - Spring Security
> - Lombok
> - Bootstrap
> - JQuery
> - Swagger 2
> - Inject
> - Embedded Mongodb
> - Cucumber

#### Task 13
#### [Spring Security ACL](https://github.com/reomor/spring-homework/tree/dev/task13)
Настроить в приложении авторизацию на уровне доменных сущностей. <br>
Ecosystem:
> - Spring Boot
> - Spring Data Jpa
> - Spring Data Mongodb
> - Spring Web
> - Thymeleaf
> - Spring Security
> - Spring Security Acl
> - Ehcache
> - H2
 
### Task 14
#### [Spring Batch](https://github.com/reomor/spring-homework/tree/dev/task14)
Разработать процедуру миграции данных из реляционного хранилища в NoSQL или наоборот
Используя Spring Batch. <br>
Ecosystem:
> - Spring Boot
> - Spring Data Mongodb
> - Spring Data Jpa
> - Spring Web
> - Spring Batch
> - Spring Shell
> - Lombok
> - H2
> - Embedded Mongodb
 
#### Task 15
#### [Spring Integration](https://github.com/reomor/spring-homework/tree/dev/task15)
Реализовать обработку доменной сущности через каналы Spring Integration. <br>
Ecosystem:
> - Spring Boot
> - Spring Integration
> - Spring Messaging
> - Lombok

#### Task 16
#### [Spring Actuator + Spring HATEOAS (HAL)](https://github.com/reomor/spring-homework/tree/dev/task16)
Реализовать использование метрик healthchecks и logfile.
Добавить поддержку HATEOAS. <br>
Ecosystem:
> - Spring Boot
> - Spring Data Jpa
> - Spring Boot Actuator
> - Spring Web
> - Spring Hateoas
> - Spring Data Rest (+HAL)

#### Task 17
#### [Spring Boot + Docker](https://github.com/reomor/spring-homework/tree/dev/task17)
Обернуть приложение и БД в docker-контейнер. <br>
Ecosystem:
> - Spring Boot
> - Spring Data Jpa
> - Spring Actuator
> - Spring Web
> - Spring Hateoas
> - Spring Data Rest
> - Postgres
> - Docker
