package task06.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task06.dao.AuthorDao;
import task06.dao.BookDao;
import task06.dao.GenreDao;
import task06.domain.Author;
import task06.domain.Book;
import task06.domain.Genre;
import task06.service.console.AuthorConsoleService;
import task06.service.console.BookConsoleService;
import task06.service.console.DaoConsoleService;
import task06.service.console.GenreConsoleService;

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
                                     BookConsoleService bookConsoleService,
                                     CommentService commentService) {
        mapping.put("genre", genreConsoleService);
        mapping.put("author", authorConsoleService);
        mapping.put("book", bookConsoleService);
        mapping.put("comment", commentService);
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

    public void getByBookId(int bookId) {
        ((CommentService)mapping.get("comment")).getByBookId(bookId);
    }

    private boolean isDao(String dao) {
        return mapping.containsKey(dao);
    }
}
