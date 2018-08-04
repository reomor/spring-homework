package task08.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task08.service.console.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class ConsoleInteractionService {

    private Map<String, DaoConsoleService> mapping = new HashMap<>();

    private final BufferedReader reader;

    @Autowired
    public ConsoleInteractionService(GenreConsoleService genreConsoleService,
                                     AuthorConsoleService authorConsoleService,
                                     BookConsoleService bookConsoleService,
                                     CommentConsoleService commentConsoleService) {
        mapping.put("genre", genreConsoleService);
        mapping.put("author", authorConsoleService);
        mapping.put("book", bookConsoleService);
        mapping.put("comment", commentConsoleService);
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
        ((CommentConsoleService)mapping.get("comment")).getByBookId(bookId);
    }

    private boolean isDao(String dao) {
        return mapping.containsKey(dao);
    }
}
