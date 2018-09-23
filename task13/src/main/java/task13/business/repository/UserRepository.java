package task13.business.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import task13.business.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findOneByEmail(String email);
}
