package task08.repository;

import org.springframework.data.repository.CrudRepository;
import task08.domain.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {
}
