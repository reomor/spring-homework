package task07.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task07.dao.AuthorDao;
import task07.dao.BookDao;
import task07.dao.GenreDao;
import task07.domain.Author;
import task07.domain.Book;
import task07.domain.Genre;
import task07.service.console.AuthorConsoleService;
import task07.service.console.BookConsoleService;
import task07.service.console.DaoConsoleService;
import task07.service.console.GenreConsoleService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConsoleInteractionService {

    private Map<String, DaoConsoleService> mapping = new HashMap<>();

    private final BufferedReader reader;

    @Autowired
    public ConsoleInteractionService(GenreConsoleService genreConsoleService,
                                     AuthorConsoleService authorConsoleService,
                                     BookConsoleService bookConsoleService) {
        mapping.put("genre", genreConsoleService);
        mapping.put("author", authorConsoleService);
        mapping.put("book", bookConsoleService);
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void create(String dao) {
        if (isDao(dao)) {
            mapping.get(dao).create(reader);
        }
    }

    public void update(String dao) {
        if (isDao(dao)) {
            mapping.get(dao).update(reader);
        }
    }

    public void delete(String dao, int id) {
        if (isDao(dao)) {
            mapping.get(dao).delete(id);
        }
        getAll(dao);
    }

    public void getById(String dao, int id) {
        if (isDao(dao)) {
            mapping.get(dao).getById(id);
        }
    }

    public void getAll(String dao) {
        if (isDao(dao)) {
            mapping.get(dao).getAll();
        }
    }

    private boolean isDao(String dao) {
        return mapping.containsKey(dao);
    }
}
