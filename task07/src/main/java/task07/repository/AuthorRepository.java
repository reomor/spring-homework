package task07.repository;

import org.springframework.data.repository.CrudRepository;
import task07.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends CrudRepository<Author, Integer> {
    @Override
    Author save(Author author);

    @Override
    void deleteById(Integer integer);

    @Override
    Optional<Author> findById(Integer integer);

    @Override
    List<Author> findAll();
}
