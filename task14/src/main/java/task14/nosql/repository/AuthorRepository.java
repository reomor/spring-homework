package task14.nosql.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import task14.nosql.domain.MongoAuthor;

public interface AuthorRepository extends MongoRepository<MongoAuthor, String> {
}
