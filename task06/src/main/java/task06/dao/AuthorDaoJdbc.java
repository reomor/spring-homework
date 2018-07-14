package task06.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import task06.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

// https://www.concretepage.com/spring-boot/spring-boot-jdbc-example
@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final JdbcOperations jdbc;
    private final NamedParameterJdbcOperations namedJdbc;

    @Autowired
    public AuthorDaoJdbc(JdbcOperations jdbcOperations, NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbc = jdbcOperations;
        this.namedJdbc = namedParameterJdbcOperations;
    }

    @Override
    public Author add(Author author) {
        return null;
    }

    @Override
    public Author update(Author author) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Author getById(int id) {
        return null;
    }

    @Override
    public List<Author> getAll() {
        return namedJdbc.query("SELECT * FROM AUTHORS", new AuthorMapper());
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String sername = rs.getString("sername");
            LocalDate dateOfBirth = rs.getDate("dateofbirth").toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String biography = rs.getString("biography");
            return new Author(id, name, sername, dateOfBirth, biography);
        }
    }
}
