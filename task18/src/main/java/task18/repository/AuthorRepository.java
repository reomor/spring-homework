package task18.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import task18.domain.Author;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "authors", path = "authors")
public interface AuthorRepository extends PagingAndSortingRepository<Author, Integer> {

    @Override
    Author save(Author author);

    @Override
    void deleteById(Integer integer);

    @Override
    Optional<Author> findById(Integer integer);

    @Override
    List<Author> findAll();
}
