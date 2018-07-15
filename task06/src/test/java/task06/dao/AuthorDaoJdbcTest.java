package task06.dao;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.junit.After;
import org.junit.Before;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class AuthorDaoJdbcTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    /*
    // https://www.mkyong.com/spring/spring-embedded-database-examples/
    // https://www.javatips.net/api/postgresql-embedded-master/src/test/java/ru/yandex/qatools/embed/postgresql/EmbeddedPostgresTest.java
    private EmbeddedPostgres postgres;

    @Before
    public  void setUp() throws Exception {
        postgres = new EmbeddedPostgres();
    }

    @After
    public  void tearDown() throws Exception {
        postgres.getProcess().ifPresent(PostgresProcess::stop);
    }
    //*/
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
        List<Author> all = authorDaoJdbc.getAll();
        assertEquals(1, all.size());
    }

    @Test
    public void exampleTest(){
        try(Connection conn = dataSource.getConnection()){
            Statement statement = conn.createStatement();
            statement.executeQuery("SELECT * FROM AUTHORS");
        }
        catch (Exception ex){
            throw new IllegalStateException(ex);
        }
    }
}