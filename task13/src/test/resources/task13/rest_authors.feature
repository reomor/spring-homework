# language: ru
@restauthors
Функция: Тестирование REST API

  Сценарий: Тестирование работы GET-endpoint для авторов
    Дано REST-контроллер для авторов
    Когда запрос от USER
    И метод GET url 'http://localhost:8080/rest/authors'
    Тогда статус 200

  Сценарий: Тестирование работы GET-endpoint для автора с id
    Дано REST-контроллер для авторов
    Когда запрос от USER
    И метод GET url 'http://localhost:8080/rest/authors/1'
    Тогда статус 200

  Сценарий: Тестирование работы POST-endpoint нового автора
    Дано REST-контроллер для авторов
    Когда запрос от USER
    И метод POST url 'http://localhost:8080/rest/authors'
    Тогда статус 403

  Сценарий: Тестирование работы PUT-endpoint существующего автора
    Дано REST-контроллер для авторов
    Когда запрос от USER
    И метод PUT url 'http://localhost:8080/rest/authors/1'
    Тогда статус 403

  Сценарий: Тестирование работы DELETE-endpoint авторов
    Дано REST-контроллер для авторов
    Когда запрос от USER
    И метод DELETE url 'http://localhost:8080/rest/authors/1'
    Тогда статус 403