package task06.dao;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import com.opentable.db.postgres.embedded.FlywayPreparer;
import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.PreparedDbRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import task06.domain.Author;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;

public class AuthorDaoJdbcTest extends AbstractDaoTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AuthorDao authorDao;

    /*
    @Rule
    public PreparedDbRule db =
            EmbeddedPostgresRules.preparedDatabase(
                    FlywayPreparer.forClasspathLocation("db/migration"));

    /*
    private EmbeddedPostgres postgres;

    @Before
    public  void setUp() throws Exception {
        postgres = EmbeddedPostgres.builder().setPort(5433).start();
    }

    @After
    public  void tearDown() throws Exception {
        postgres.close();
    }
    /*
    @Before
    public void setup() {
        database = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
    }
//*/
    @Test
    public void getAll() {
        List<Author> all = authorDao.getAll();
        assertEquals(1, all.size());
    }

    @Test
    public void check_tableShouldExist(){
        try(Connection conn = dataSource.getConnection()){
            Statement statement = conn.createStatement();
            statement.executeQuery("SELECT * FROM AUTHORS");
        }
        catch (Exception ex){
            throw new IllegalStateException(ex);
        }
    }
}