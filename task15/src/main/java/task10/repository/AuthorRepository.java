package task10.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import task10.domain.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {
}
