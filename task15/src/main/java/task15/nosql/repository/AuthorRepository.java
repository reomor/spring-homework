package task15.nosql.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import task15.nosql.domain.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {
}
