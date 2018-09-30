package task15.sql.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import task15.sql.domain.RdbmsBook;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
@Repository
public class BookDaoJpa implements BookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(RdbmsBook book) {
        em.persist(book);
    }

    @Override
    public RdbmsBook update(RdbmsBook book) {
        return em.merge(book);
    }

    @Override
    public void delete(int id) {
        RdbmsBook book = getById(id);
        em.remove(book);
    }

    @Override
    public RdbmsBook getById(int id) {
        return em.find(RdbmsBook.class, id);
    }

    @Override
    public List<RdbmsBook> getAll() {
        TypedQuery<RdbmsBook> query = em.createQuery("SELECT b FROM Book b", RdbmsBook.class);
        return query.getResultList();
    }
}
