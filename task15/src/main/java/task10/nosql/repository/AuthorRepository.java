package task10.nosql.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import task10.nosql.domain.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {
}
