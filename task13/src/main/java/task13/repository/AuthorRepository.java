package task13.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import task13.domain.Author;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String> {
}
