package task06.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import task06.domain.Author;
import task06.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {

    private final JdbcOperations jdbc;
    private final NamedParameterJdbcOperations namedJdbc;

    @Autowired
    public BookDaoJdbc(JdbcOperations jdbc, NamedParameterJdbcOperations namedJdbc) {
        this.jdbc = jdbc;
        this.namedJdbc = namedJdbc;
    }

    @Override
    public int create(Book book) {
        return 0;
    }

    @Override
    public int update(Book book) {
        return 0;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Book getById(int id) {
        return null;
    }

    @Override
    public List<Book> getAll() {
        return null;
    }

    @Override
    public List<Book> getBooksByAuthor(Author author) {
        final Map<String, Object> params = Collections.singletonMap("id", author.getId());
        return namedJdbc.query("SELECT * FROM BOOKS INNER JOIN AUTHOR_BOOK " +
                "ON BOOKS.ID = AUTHOR_BOOK.ID_BOOK " +
                "WHERE AUTHOR_BOOK.ID_AUTHOR=:id", params, new BookMapper());
        //return namedJdbc.query("SELECT * FROM AUTHORS INNER JOIN AUTHOR_BOOK ON AUTHORS.ID = AUTHOR_BOOK.ID_AUTHOR WHERE AUTHORS.ID=:id");
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt("id");
            return null;
        }
    }
}
