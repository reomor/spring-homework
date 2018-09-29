package task15.sql.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import task15.sql.domain.Author;

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
    public void create(Author author) {
        em.persist(author);
    }

    @Override
    public Author update(Author author) {
        return em.merge(author);
    }

    @Override
    public void delete(int id) {
        Author author = getById(id);
        em.remove(author);
    }

    @Override
    public Author getById(int id) {
        return em.find(Author.class, id);
    }

    @Override
    public List<Author> getAll() {
        TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a", Author.class);
        return query.getResultList();
    }
}
