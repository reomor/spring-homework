package task07.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import task07.service.ConsoleInteractionService;
import task07.service.console.DaoConsoleService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ShellComponent
public class Commands {

    private final ConsoleInteractionService consoleInteractionService;

    @Autowired
    public Commands(ConsoleInteractionService consoleInteractionService) {
        this.consoleInteractionService = consoleInteractionService;
    }

    @ShellMethod(value = "Create", key = {"create"})
    public void create (
            @ShellOption("-d") String objectName
    ) {
        consoleInteractionService.create(objectName);
    }

    @ShellMethod("Update")
    public void update (
            @ShellOption("-d") String objectName
    ) {
        consoleInteractionService.update(objectName);
    }

    @ShellMethod("Get by id")
    public void delete (
            @ShellOption("-d") String objectName,
            @ShellOption("-id") int id
    ) {
        consoleInteractionService.delete(objectName, id);
    }

    @ShellMethod("Get by id")
    public void byid (
            @ShellOption("-d") String objectName,
            @ShellOption("-id") int id
    ) {
        consoleInteractionService.getById(objectName, id);
    }

    @ShellMethod("Get all values")
    public void all(String objectName) {
        consoleInteractionService.getAll(objectName);
    }

    /*
    @ShellMethod("Get by external id")
    public void getbei (
            @ShellOption("-d") String objectName,
            @ShellOption("-id") int id
    ) {
        consoleInteractionService.getByExternalId(objectName, id);
    }
    //*/
}
