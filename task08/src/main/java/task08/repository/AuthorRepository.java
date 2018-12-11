package task08.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import task08.domain.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {
}
