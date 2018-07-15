package task06.dao;

import task06.domain.Author;
import task06.domain.Book;

import java.util.List;

public interface BookDao {
    List<Book> getBooksByAuthor(Author author);
}
