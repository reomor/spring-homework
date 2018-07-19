package task06.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import task06.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoJdbc implements GenreDao {

    private final JdbcOperations jdbc;
    private final NamedParameterJdbcOperations namedJdbc;

    @Autowired
    public GenreDaoJdbc(JdbcOperations jdbc, NamedParameterJdbcOperations namedJdbc) {
        this.jdbc = jdbc;
        this.namedJdbc = namedJdbc;
    }

    @Override
    public int create(Genre genre) {
        final Map<String, Object> params = new HashMap<>();
        params.put("id", genre.getId());
        params.put("name", genre.getName());
        params.put("description", genre.getDescription());
        return namedJdbc.update("INSERT INTO GENRES(ID, NAME, DESCRIPTION) VALUES (:id, :name, :description)", params);
    }

    @Override
    public int update(Genre genre) {
        final Map<String, Object> params = new HashMap<>();
        params.put("id", genre.getId());
        params.put("name", genre.getName());
        params.put("description", genre.getDescription());
        return namedJdbc.update("UPDATE GENRES SET NAME = :name, DESCRIPTION = :description WHERE id=:id", params);
    }

    @Override
    public boolean delete(int id) {
        final Map<String, Object> params = Collections.singletonMap("id", id);
        return namedJdbc.update("DELETE FROM GENRES WHERE id=:id", params) != 0;
    }

    @Override
    public Genre getById(int id) {
        final Map<String, Object> params = Collections.singletonMap("id", id);
        return namedJdbc.queryForObject("select * from GENRES WHERE id=:id", params, new GenreMapper());
    }

    @Override
    public List<Genre> getAll() {
        return namedJdbc.query("SELECT * FROM GENRES", new GenreMapper());
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String description = rs.getString("description");
            return new Genre(id, name, description);
        }
    }
}
