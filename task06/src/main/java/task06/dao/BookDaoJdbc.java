package task06.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import task06.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {

    private final JdbcOperations jdbc;
    private final NamedParameterJdbcOperations namedJdbc;
    // for example using one mapper per repo
    private final BookMapper bookMapper;

    @Autowired
    public BookDaoJdbc(JdbcOperations jdbc, NamedParameterJdbcOperations namedJdbc) {
        this.jdbc = jdbc;
        this.namedJdbc = namedJdbc;
        this.bookMapper = new BookMapper();
    }

    @Override
    public int create(Book book) {
        final Map<String, Object> params = new HashMap<>();
        params.put("title", book.getTitle());
        params.put("id_genre", book.getIdGenre());
        params.put("isbn", book.getIsbn());
        params.put("description", book.getDescription());
        return namedJdbc.update("INSERT INTO BOOKS" +
                " (TITLE, ID_GENRE, ISBN, DESCRIPTION)" +
                " VALUES (:title, :id_genre, :isbn, :description)", params);
    }

    @Override
    public int update(Book book) {
        final Map<String, Object> params = new HashMap<>();
        params.put("id", book.getId());
        params.put("title", book.getTitle());
        params.put("id_genre", book.getIdGenre());
        params.put("isbn", book.getIsbn());
        params.put("description", book.getDescription());
        return namedJdbc.update("UPDATE BOOKS SET" +
                " TITLE=:title, ID_GENRE=:id_genre, ISBN=:isbn, DESCRIPTION=:description" +
                " WHERE ID=:id", params);
    }

    @Override
    public boolean delete(int id) {
        final Map<String, Object> params = Collections.singletonMap("id", id);
        return namedJdbc.update("DELETE FROM BOOKS WHERE ID=:id", params) != 0;
    }

    @Override
    public Book getById(int id) {
        final Map<String, Object> params = Collections.singletonMap("id", id);
        return namedJdbc.queryForObject("SELECT * FROM BOOKS WHERE ID=:id", params, bookMapper);
    }

    @Override
    public List<Book> getAll() {
        return namedJdbc.query("SELECT * FROM BOOKS", bookMapper);
    }

    @Override
    public List<Book> getByAuthor(int authorId) {
        final Map<String, Object> params = Collections.singletonMap("id", authorId);
        return namedJdbc.query("SELECT * FROM BOOKS INNER JOIN AUTHOR_BOOK " +
                "ON BOOKS.ID = AUTHOR_BOOK.ID_BOOK " +
                "WHERE AUTHOR_BOOK.ID_AUTHOR=:id", params, bookMapper);
        //return namedJdbc.query("SELECT * FROM AUTHORS INNER JOIN AUTHOR_BOOK ON AUTHORS.ID = AUTHOR_BOOK.ID_AUTHOR WHERE AUTHORS.ID=:id");
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            int idGenre = rs.getInt("id_genre");
            String isbn = rs.getString("isbn");
            String description = rs.getString("description");
            return new Book(id, title, idGenre, isbn, description);
        }
    }
}
