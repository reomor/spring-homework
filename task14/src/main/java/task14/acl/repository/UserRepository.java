package task14.acl.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import task14.acl.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findOneByEmail(String email);

    @Override
    User save(User user);

    @Override
    @PostAuthorize("hasAuthority('ROLE_ADMIN') || hasPermission(returnObject, 'READ')")
    Optional<User> findById(Long id);

    @Override
    @PreAuthorize("hasPermission(#user, 'DELETE')")
    void delete(@Param("user") User user);

    @Override
    @PostFilter("hasPermission(filterObject, 'READ')")
    List<User> findAll();
}
