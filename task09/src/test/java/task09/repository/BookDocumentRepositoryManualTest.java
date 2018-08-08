package task09.repository;

import com.mongodb.MongoClient;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import task09.domain.BookDocument;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class BookDocumentRepositoryManualTest extends TestCase {

    private static final MongodStarter starter = MongodStarter.getDefaultInstance();

    private MongodExecutable _mongodExe;
    private MongodProcess _mongod;

    private MongoClient _mongo;

    @Autowired
    private BookDocumentRepository repository;

    @Override
    protected void setUp() throws Exception {
        _mongodExe = starter.prepare(new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net("localhost", 12345, Network.localhostIsIPv6()))
                .build());
        _mongod = _mongodExe.start();

        super.setUp();

        _mongo = new MongoClient("localhost", 12345);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        _mongod.stop();
        _mongodExe.stop();
    }

    @Test
    public void shouldCreateGame() {
        BookDocument bookDocument = new BookDocument("NewBook");
        BookDocument savedBook = repository.save(bookDocument);
        assertNotNull(savedBook.getId());
        assertEquals(bookDocument.getTitle(), savedBook.getTitle());
    }
}