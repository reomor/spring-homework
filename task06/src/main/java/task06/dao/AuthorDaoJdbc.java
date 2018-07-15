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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public int create(Author author) {
        final Map<String, Object> params = new HashMap<>();
        params.put("id", author.getId());
        params.put("name", author.getName());
        params.put("sername", author.getSername());
        params.put("dateOfBirth", author.getDateOfBirth());
        params.put("biography", author.getBiography());
        return namedJdbc.update("INSERT INTO AUTHORS (ID, NAME, SERNAME, DATEOFBIRTH, BIOGRAPHY) VALUES\n" +
                "  (:id, :name, :sername, :dateOfBirth, :biography)", params);
    }

    @Override
    public int update(Author author) {
        final Map<String, Object> params = new HashMap<>();
        params.put("id", author.getId());
        params.put("name", author.getName());
        params.put("sername", author.getSername());
        params.put("dateOfBirth", author.getDateOfBirth());
        params.put("biography", author.getBiography());
        return namedJdbc.update("UPDATE AUTHORS SET NAME=:name, SERNAME=:sername, DATEOFBIRTH=:dateOfBirth, BIOGRAPHY=:biography WHERE id=:id", params);
    }

    @Override
    public boolean delete(int id) {
        final Map<String, Object> params = Collections.singletonMap("id", id);
        return namedJdbc.update("DELETE FROM AUTHORS WHERE id=:id", params) != 0;
    }

    @Override
    public Author getById(int id) {
        final Map<String, Object> params = Collections.singletonMap("id", id);
        return namedJdbc.queryForObject("SELECT * FROM AUTHORS WHERE id=:id", params, new AuthorMapper());
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
            LocalDate dateOfBirth = rs.getDate("dateOfBirth").toLocalDate();
            String biography = rs.getString("biography");
            return new Author(id, name, sername, dateOfBirth, biography);
        }
    }
}
