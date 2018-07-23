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
    ) throws IOException {
        consoleInteractionService.create(objectName);
    }

    @ShellMethod("Update")
    public void update (
            @ShellOption("-d") String objectName
    ) throws IOException {
        //consoleInteractionService.create(objectName);
    }

    @ShellMethod("Get by id")
    public void del (
            @ShellOption("-d") String objectName,
            @ShellOption("-id") int id
    ) {
        consoleInteractionService.getById(objectName, id);
    }

    @ShellMethod("Get by id")
    public void getbi (
            @ShellOption("-d") String objectName,
            @ShellOption("-id") int id
    ) {
        consoleInteractionService.getById(objectName, id);
    }

    @ShellMethod("Get all values")
    public void getall(String objectName) {
        consoleInteractionService.getAll(objectName);
    }

    @ShellMethod("Get by external id")
    public void getbei (
            @ShellOption("-d") String objectName,
            @ShellOption("-id") int id
    ) {
        consoleInteractionService.getByExternalId(objectName, id);
    }
}
