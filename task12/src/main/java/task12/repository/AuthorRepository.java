package task12.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import task12.domain.Author;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String> {
}
