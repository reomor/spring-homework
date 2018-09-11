package task12.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import task12.domain.Author;

public interface AuthorReactiveRepository extends ReactiveMongoRepository<Author, String> {
}
