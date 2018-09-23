package task13.business.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import task13.business.domain.Author;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String> {
}
