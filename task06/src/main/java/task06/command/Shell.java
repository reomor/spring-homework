package task06.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import task06.dao.BookDao;

@ShellComponent
public class Shell {

    @Autowired
    private BookDao bookDao;

    @ShellMethod("GetAll")
    public void all() {
        System.out.println(bookDao.getAll());
    }
}
