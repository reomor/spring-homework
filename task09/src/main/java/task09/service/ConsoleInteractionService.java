package task09.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task09.service.console.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class ConsoleInteractionService {

    private Map<String, RepositoryConsoleService> mapping = new HashMap<>();

    private final BufferedReader reader;

    @Autowired
    public ConsoleInteractionService(GenreRepositoryConsoleService genreRepositoryConsoleService,
                                     AuthorRepositoryConsoleService authorRepositoryConsoleService,
                                     BookRepositoryConsoleService bookRepositoryConsoleService,
                                     CommentRepositoryConsoleService commentRepositoryConsoleService) {
        mapping.put("genre", genreRepositoryConsoleService);
        mapping.put("author", authorRepositoryConsoleService);
        mapping.put("book", bookRepositoryConsoleService);
        mapping.put("comment", commentRepositoryConsoleService);
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
        ((CommentRepositoryConsoleService) mapping.get("comment")).getByBookId(bookId);
    }

    private boolean isDao(String dao) {
        return mapping.containsKey(dao);
    }
}
