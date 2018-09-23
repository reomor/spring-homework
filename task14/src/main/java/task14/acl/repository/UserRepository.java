package task14.acl.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import task14.acl.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    User findOneByEmail(String email);
}
