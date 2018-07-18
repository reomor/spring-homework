package task06.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import task06.dao.AuthorDao;
import task06.dao.BookDao;
import task06.dao.GenreDao;
import task06.service.ConsoleInteractionService;

@ShellComponent
public class Commands {

    private final ConsoleInteractionService consoleInteractionService;

    @Autowired
    public Commands(ConsoleInteractionService consoleInteractionService) {
        this.consoleInteractionService = consoleInteractionService;
    }

    @ShellMethod("Get all values")
    public void getall(String obejctName) {
        consoleInteractionService.getall(obejctName);
    }
}
