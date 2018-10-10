package task17.repository;

import org.springframework.data.repository.CrudRepository;
import task17.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Integer> {
    @Override
    Book save(Book s);

    @Override
    void deleteById(Integer integer);

    @Override
    Optional<Book> findById(Integer integer);

    @Override
    List<Book> findAll();
}
