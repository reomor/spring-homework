package task07.service.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task07.dao.BookDao;
import task07.domain.Book;

import java.util.List;

@Service
public class BookConsoleService implements DaoConsoleService<Book> {

    private final BookDao bookDao;

    @Autowired
    public BookConsoleService(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public void create() {

    }

    @Override
    public Book update(Book object) {
        return null;
    }

    @Override
    public void delete(int objectId) {

    }

    @Override
    public Book getById(int id) {
        return null;
    }

    @Override
    public List<Book> getAll() {
        return null;
    }
}
