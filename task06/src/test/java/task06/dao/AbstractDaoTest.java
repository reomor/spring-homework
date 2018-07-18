package task06.dao;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;


@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@TestPropertySource("classpath:application-test.properties")
public abstract class AbstractDaoTest {

    private static EmbeddedPostgres embeddedPostgres;

    // http://blog.rizvn.com/2018/04/testing-database-querries-with-embedded.html
    @BeforeClass
    public static void init() throws Exception {
        if (embeddedPostgres == null) {
            embeddedPostgres = EmbeddedPostgres.builder().setPort(9999).start();
        }
    }

    @AfterClass
    public static void finish() throws IOException {
        if (embeddedPostgres != null) {
            embeddedPostgres.close();
        }
    }
}