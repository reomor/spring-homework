package task10.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task10.service.console.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class ConsoleInteractionService {

    private final String BOOK_DAO = "book";
    private final String AUTHOR_DAO = "author";

    private final AuthorConsoleService authorConsoleService;
    private final BookConsoleService bookConsoleService;

    private final BufferedReader reader;

    @Autowired
    public ConsoleInteractionService(AuthorConsoleService authorConsoleService,
                                     BookConsoleService bookConsoleService) {
        this.authorConsoleService = authorConsoleService;
        this.bookConsoleService = bookConsoleService;
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void create(String dao) {
        if (BOOK_DAO.equals(dao)) {
            bookConsoleService.create(reader);
        } else if (AUTHOR_DAO.equals(dao)) {
            authorConsoleService.create(reader);
        }
    }

    public void update(String dao) {
        if (BOOK_DAO.equals(dao)) {
            bookConsoleService.update(reader);
        } else if (AUTHOR_DAO.equals(dao)) {
            authorConsoleService.update(reader);
        }
    }

    public void delete(String dao, String id) {
        if (BOOK_DAO.equals(dao)) {
            bookConsoleService.delete(id);
        } else if (AUTHOR_DAO.equals(dao)) {
            authorConsoleService.delete(id);
        }
        getAll(dao);
    }

    public void getById(String dao, String id) {
        if (BOOK_DAO.equals(dao)) {
            bookConsoleService.getById(id);
        } else if (AUTHOR_DAO.equals(dao)) {
            authorConsoleService.getById(id);
        }
    }

    public void getAll(String dao) {
        if (BOOK_DAO.equals(dao)) {
            bookConsoleService.getAll();
        } else if (AUTHOR_DAO.equals(dao)) {
            authorConsoleService.getAll();
        }
    }

    public void comment(String bookId) {
        bookConsoleService.setComment(reader, bookId);
    }

    public void comments(String bookId) {
        bookConsoleService.getComments(bookId);
    }
}
