package task14.sql.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import task14.sql.domain.RdbmsAuthor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class AuthorDaoJpa implements AuthorDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(RdbmsAuthor author) {
        em.persist(author);
    }

    @Override
    public RdbmsAuthor update(RdbmsAuthor author) {
        return em.merge(author);
    }

    @Override
    public void delete(int id) {
        RdbmsAuthor author = getById(id);
        em.remove(author);
    }

    @Override
    public RdbmsAuthor getById(int id) {
        return em.find(RdbmsAuthor.class, id);
    }

    @Override
    public List<RdbmsAuthor> getAll() {
        TypedQuery<RdbmsAuthor> query = em.createQuery("SELECT a FROM RdbmsAuthor a", RdbmsAuthor.class);
        return query.getResultList();
    }
}
