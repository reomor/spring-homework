package task13.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import task13.domain.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {
}
