package task04.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import task04.service.ConsoleInteractionService;
import task04.service.TestingProcessService;

@ShellComponent
public class Commands {

    private final TestingProcessService testingProcessService;
    private final ConsoleInteractionService consoleInteractionService;

    @Autowired
    public Commands(TestingProcessService testingProcessService, ConsoleInteractionService consoleInteractionService) {
        this.testingProcessService = testingProcessService;
        this.consoleInteractionService = consoleInteractionService;
    }

    @ShellMethod("Test function")
    public void runtest() {
        testingProcessService.processTest();
    }
}
