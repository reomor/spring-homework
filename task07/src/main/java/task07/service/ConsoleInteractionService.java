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

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final BufferedReader reader;

    @Autowired
    public ConsoleInteractionService(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao,
                                     GenreConsoleService genreConsoleService,
                                     AuthorConsoleService authorConsoleService,
                                     BookConsoleService bookConsoleService) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        mapping.put("genre", genreConsoleService);
        mapping.put("author", authorConsoleService);
        mapping.put("book", bookConsoleService);
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void create(String dao) throws IOException {
        if (isDao(dao)) {
            mapping.get(dao).create(reader);
        }
        /*
        if ("book".equals(dao)) {
            Book book = readBook();
            int genreId = choose("genre");
            int authorId = choose("author");
            Author author = authorDao.getById(authorId);
            book.setIdGenre(genreId);
            book.setAuthor(author);
            bookDao.create(book);
        } else if ("author".equals(dao)) {
            Author author = readAuthor();
            authorDao.create(author);
        } else if ("genre".equals(dao)) {
            Genre genre = readGenre();
            genreDao.create(genre);
        }
        getAll(dao);
        //*/
    }

    public void delete(String dao, int id) {
        if ("book".equals(dao)) {
            bookDao.delete(id);
        } else if ("author".equals(dao)) {
            authorDao.delete(id);
        } else if ("genre".equals(dao)) {
            genreDao.delete(id);
        }
        getAll(dao);
    }

    public void getById(String dao, int id) {
        if ("book".equals(dao)) {
            System.out.println(bookDao.getById(id));
        } else if ("author".equals(dao)) {
            System.out.println(authorDao.getById(id));
        } else if ("genre".equals(dao)) {
            System.out.println(genreDao.getById(id));
        }
    }

    public void getAll(String dao) {
        if ("book".equals(dao)) {
            printList(bookDao.getAll());
        } else if ("author".equals(dao)) {
            printList(authorDao.getAll());
        } else if ("genre".equals(dao)) {
            printList(genreDao.getAll());
        }
    }

    public void getByExternalId(String dao, int externalId) {
        if ("book".equals(dao)) {
            printList(bookDao.getByAuthorId(externalId));
        } else if ("author".equals(dao)) {
            printList(authorDao.getByBookId(externalId));
        }
    }

    private boolean isDao(String dao) {
        return mapping.containsKey(dao);
    }

    private int choose(String dao) throws IOException {
        if ("author".equals(dao)) {
            printList(authorDao.getAll());
            System.out.println("Choose an author by id:");
        } else if ("genre".equals(dao)) {
            System.out.println("Choose a genre by id:");
            printList(genreDao.getAll());
        }
        return Integer.parseInt(reader.readLine());
    }

    private <T> void printList(List<T> list) {
        System.out.println("=== list begin ===");
        for (T element : list) {
            System.out.println(element);
        }
        System.out.println("=== list end ===");
    }
}
