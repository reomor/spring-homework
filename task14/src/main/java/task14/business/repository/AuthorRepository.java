package task14.business.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import task14.business.domain.Author;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String> {
}
