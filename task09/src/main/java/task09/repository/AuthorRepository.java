package task09.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import task09.domain.Author;

public interface AuthorRepository extends MongoRepository<Author, Integer> {
}
