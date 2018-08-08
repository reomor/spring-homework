package task09.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import task09.domain.BookDocument;

public interface BookDocumentRepository extends MongoRepository<BookDocument, Long> {
}
