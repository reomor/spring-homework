package task06.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task06.dao.AuthorDao;
import task06.dao.BookDao;
import task06.dao.GenreDao;

import java.util.List;

@Service
public class ConsoleInteractionService {
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Autowired
    public ConsoleInteractionService(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    public void create(String dao) {

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

    private <T> void printList(List<T> list) {
        System.out.println("=== list begin ===");
        for (T element : list) {
            System.out.println(element);
        }
        System.out.println("=== list end ===");
    }
}
