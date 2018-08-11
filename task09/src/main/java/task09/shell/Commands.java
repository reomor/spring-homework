package task09.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import task09.service.ConsoleInteractionService;

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

    @ShellMethod(value = "Update", key = {"update"})
    public void update(
            @ShellOption("-d") String objectName
    ) {
        consoleInteractionService.update(objectName);
    }

    @ShellMethod(value = "Delete", key = {"delete"})
    public void delete(
            @ShellOption("-d") String objectName,
            @ShellOption("-id") String id
    ) {
        consoleInteractionService.delete(objectName, id);
    }

    @ShellMethod(value = "Get by id", key = {"byid", "get"})
    public void byid(
            @ShellOption("-d") String objectName,
            @ShellOption("-id") String id
    ) {
        consoleInteractionService.getById(objectName, id);
    }

    @ShellMethod(value = "Get all values", key = {"all"})
    public void all(
            @ShellOption("-d") String objectName
    ) {
        consoleInteractionService.getAll(objectName);
    }

    @ShellMethod(value = "Comment a book", key = {"comment"})
    public void comment(
            @ShellOption("-bId") String bookId) {
        consoleInteractionService.comment(bookId);
    }

    @ShellMethod(value = "Get book comments", key = {"comments"})
    public void comments(
            @ShellOption("-bId") String bookId) {
        consoleInteractionService.comments(bookId);
    }
}
