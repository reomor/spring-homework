package task11.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import task11.domain.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {
}
