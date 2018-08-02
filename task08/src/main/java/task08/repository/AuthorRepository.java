package task08.repository;

import org.springframework.data.repository.CrudRepository;
import task08.domain.Author;

import java.util.Optional;

public interface AuthorRepository extends CrudRepository<Author, Integer> {
    @Override
    <S extends Author> S save(S s);

    @Override
    void deleteById(Integer integer);

    @Override
    Optional<Author> findById(Integer integer);

    @Override
    Iterable<Author> findAll();
}
