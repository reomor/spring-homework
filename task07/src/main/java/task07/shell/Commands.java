package task07.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import task07.service.ConsoleInteractionService;

@ShellComponent
public class Commands {

    private final ConsoleInteractionService consoleInteractionService;

    @Autowired
    public Commands(ConsoleInteractionService consoleInteractionService) {
        this.consoleInteractionService = consoleInteractionService;
    }

    @ShellMethod(value = "Create", key = {"create"})
    public void create(
            @ShellOption("-d") String objectName
    ) {
        consoleInteractionService.create(objectName);
    }

    @ShellMethod("Update")
    public void update(
            @ShellOption("-d") String objectName
    ) {
        consoleInteractionService.update(objectName);
    }

    @ShellMethod("Get by id")
    public void delete(
            @ShellOption("-d") String objectName,
            @ShellOption("-id") int id
    ) {
        consoleInteractionService.delete(objectName, id);
    }

    @ShellMethod("Get by id")
    public void byid(
            @ShellOption("-d") String objectName,
            @ShellOption("-id") int id
    ) {
        consoleInteractionService.getById(objectName, id);
    }

    @ShellMethod("Get all values")
    public void all(
            @ShellOption("-d") String objectName
    ) {
        consoleInteractionService.getAll(objectName);
    }

    @ShellMethod("Get all comments by book")
    public void comments(
            @ShellOption("-bId") int bookId) {
        consoleInteractionService.getByBookId(bookId);
    }
}
