package task06.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
        SqlParameterSource namedParameters = new MapSqlParameterSource();
        ((MapSqlParameterSource) namedParameters).addValue("title", book.getTitle());
        ((MapSqlParameterSource) namedParameters).addValue("id_genre", book.getIdGenre());
        ((MapSqlParameterSource) namedParameters).addValue("isbn", book.getIsbn());
        ((MapSqlParameterSource) namedParameters).addValue("description", book.getDescription());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedJdbc.update("INSERT INTO BOOKS" +
                " (TITLE, ID_GENRE, ISBN, DESCRIPTION)" +
                " VALUES (:title, :id_genre, :isbn, :description)", namedParameters, keyHolder);
        final int key = (Integer) keyHolder.getKeys().get("id");
        book.setId(key);
        createRelation(book);
        return key;
    }

    private void createRelation(Book book) {
        final Map<String, Object> params = new HashMap<>();
        params.put("id_author", book.getAuthor().getId());
        params.put("id_book", book.getId());
        namedJdbc.update("INSERT INTO AUTHOR_BOOK" +
                " (ID_AUTHOR, ID_BOOK)" +
                " VALUES (:id_author, :id_book)", params);
    }

    @Override
    public int getRelatedId(int bookId) {
        return jdbc.queryForObject("SELECT ID_AUTHOR FROM AUTHOR_BOOK WHERE ID_BOOK=?", new Object[] {bookId}, Integer.class);
    }

    @Override
    public void update(Book book) {
        final Map<String, Object> params = new HashMap<>();
        params.put("id", book.getId());
        params.put("title", book.getTitle());
        params.put("id_genre", book.getIdGenre());
        params.put("isbn", book.getIsbn());
        params.put("description", book.getDescription());
        namedJdbc.update("UPDATE BOOKS SET" +
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
    public List<Book> getByAuthorId(int authorId) {
        final Map<String, Object> params = Collections.singletonMap("id", authorId);
        return namedJdbc.query("SELECT * FROM BOOKS INNER JOIN AUTHOR_BOOK " +
                "ON BOOKS.ID=AUTHOR_BOOK.ID_BOOK " +
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
