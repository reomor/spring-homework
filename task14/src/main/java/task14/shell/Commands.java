package task14.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import task14.service.JobService;

@ShellComponent
public class Commands {

    private final JobService jobService;

    @Autowired
    public Commands(JobService jobService) {
        this.jobService = jobService;
    }

    @ShellMethod(value = "Migrate from [NoSQL, SQL] to [SQL, NoSQL]", key = {"restart"})
    public void restart(
            @ShellOption("-d") String objectName
    ) {
        switch (objectName) {
            case "nss":
                jobService.start("noSqlToSqlMigration");
                break;
            default:
                System.out.println("Parameter is not supported");
        }
    }
}