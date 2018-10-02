package task16.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import task16.service.ConsoleInteractionService;

import java.io.IOException;

@ShellComponent
public class Commands {

    private final ConsoleInteractionService consoleInteractionService;

    @Autowired
    public Commands(ConsoleInteractionService consoleInteractionService) {
        this.consoleInteractionService = consoleInteractionService;
    }

    @ShellMethod("Create")
    public void create (
            @ShellOption("-d") String obejctName
    ) throws IOException {
        consoleInteractionService.create(obejctName);
    }

    @ShellMethod("Update")
    public void update (
            @ShellOption("-d") String obejctName
    ) throws IOException {
        //consoleInteractionService.create(obejctName);
    }

    @ShellMethod("Get by id")
    public void del (
            @ShellOption("-d") String obejctName,
            @ShellOption("-id") int id
    ) {
        consoleInteractionService.getById(obejctName, id);
    }

    @ShellMethod("Get by id")
    public void getbi (
            @ShellOption("-d") String obejctName,
            @ShellOption("-id") int id
    ) {
        consoleInteractionService.getById(obejctName, id);
    }

    @ShellMethod("Get all values")
    public void getall(String obejctName) {
        consoleInteractionService.getAll(obejctName);
    }

    @ShellMethod("Get by external id")
    public void getbei (
            @ShellOption("-d") String obejctName,
            @ShellOption("-id") int id
    ) {
        consoleInteractionService.getByExternalId(obejctName, id);
    }
}
