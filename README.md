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
 
### Task 12
Ecosystem:
 - Spring Boot
 - Spring Data Mongodb Reactive
 - Spring WebFlux
 - Lombok
 - Embedded Mongodb
 - Reactor
 
### Task 13
Ecosystem:
 - Spring Boot
 - Spring Data Mongodb
 - Spring Web
 - Thymeleaf
 - Spring Security
 - Lombok
 - Bootstrap
 - JQuery
 - Swagger 2
 - Inject
 - Embedded Mongo
 - Cucumber

### Task 14
Ecosystem:
 - Spring Boot
 - Spring Data Jpa
 - Spring Data Mongodb
 - Spring Web
 - Thymeleaf
 - Spring Security
 - Spring Security Acl
 - Ehcache
 - H2
 
### Task 15
Ecosystem:
 - Spring Boot
 - Spring Data Mongodb
 - Spring Data Jpa
 - Spring Web
 - Spring Batch
 - Spring Shell
 - Lombok
 - H2
 - Embedded Mongodb
 
### Task 16
Ecosystem:
 - Spring Boot
 - Spring Integration
 - Spring Messaging
 - Lombok

### Task 17
Ecosystem:
 - Spring Boot
 - Spring Data Jpa
 - Spring Boot Actuator
 - Spring Web
 - Spring Hateoas
 - Spring Data Rest (+HAL)

### Task 18
Ecosystem:
 - Spring Boot
 - Spring Data Jpa
 - Spring Actuator
 - Spring Web
 - Spring Hateoas
 - Spring Data Rest
 - Postgres
 - Docker
