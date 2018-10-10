package task17.repository;

import org.springframework.data.repository.CrudRepository;
import task17.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends CrudRepository<Genre, Integer> {
    @Override
    Genre save(Genre s);

    @Override
    void deleteById(Integer integer);

    @Override
    Optional<Genre> findById(Integer integer);

    @Override
    List<Genre> findAll();
}
