package task05.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import task05.service.ConsoleInteractionService;

@ShellComponent
public class Commands {

    private final ConsoleInteractionService consoleInteractionService;

    @Autowired
    public Commands(ConsoleInteractionService consoleInteractionService) {
        this.consoleInteractionService = consoleInteractionService;
    }

    @ShellMethod("Test function")
    public String huest(@ShellOption String hui) {
        consoleInteractionService.askLocale();
        return "test";
    }

    @ShellMethod("Test function")
    public void hui() {
        consoleInteractionService.askLocale();
    }
}
