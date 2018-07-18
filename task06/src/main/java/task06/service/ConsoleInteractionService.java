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

    public void getall(String dao) {
        if ("book".equals(dao)) {
            printList(bookDao.getAll());
        } else if ("author".equals(dao)) {
            printList(authorDao.getAll());
        } else if ("genre".equals(dao)) {
            printList(genreDao.getAll());
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
