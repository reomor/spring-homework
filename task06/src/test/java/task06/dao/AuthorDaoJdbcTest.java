package task06.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Test
    public void getAll() {
        List<Author> all = authorDao.getAll();
        assertEquals(1, all.size());
    }

    @Test
    public void testCheck_tableShouldExist(){
        try (Connection conn = dataSource.getConnection()) {
            Statement statement = conn.createStatement();
            statement.executeQuery("SELECT * FROM AUTHORS");
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }
}