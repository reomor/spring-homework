package task11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import task11.domain.Author;

public interface AuthorReactiveRepository extends ReactiveMongoRepository<Author, String> {
}
