package task12.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import task12.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findOneByEmail(String email);
}
