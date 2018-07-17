package task06.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import task06.dao.AuthorDao;
import task06.dao.BookDao;
import task06.dao.GenreDao;

@ShellComponent
public class Commands {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Autowired
    public Commands(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @ShellMethod("Get all values")
    public void getall(String dao) {
        if ("book".equals(dao)) {
            System.out.println(bookDao.getAll());
        } else if ("author".equals(dao)) {
            System.out.println(authorDao.getAll());
        } else if ("genre".equals(dao)) {
            System.out.println(genreDao.getAll());
        }
    }
}
