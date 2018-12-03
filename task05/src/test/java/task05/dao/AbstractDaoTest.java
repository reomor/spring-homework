package task06.dao;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

/*@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")*/
public class AbstractDaoTest {

    private static EmbeddedPostgres embeddedPostgres;

    // http://blog.rizvn.com/2018/04/testing-database-querries-with-embedded.html
    @BeforeClass
    public static void init() throws Exception {
        if (embeddedPostgres == null) {
            embeddedPostgres = EmbeddedPostgres.builder().setPort(9999).start();

            try (Connection conn = embeddedPostgres.getPostgresDatabase().getConnection()) {
                Statement statement = conn.createStatement();
                statement.execute("CREATE DATABASE TASK06");
            }
        }
    }
}