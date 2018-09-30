package task15.sql.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import task15.sql.domain.RdbmsComment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
@Repository
public class CommentDaoJpa implements CommentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(RdbmsComment comment) {
        em.persist(comment);
    }

    @Override
    public void delete(int id) {
        RdbmsComment comment = getById(id);
        em.remove(comment);
    }

    @Override
    public RdbmsComment getById(int id) {
        return em.find(RdbmsComment.class, id);
    }

    @Override
    public List<RdbmsComment> getByBookId(int bookId) {
        TypedQuery<RdbmsComment> query = em.createQuery("SELECT c FROM RdbmsComment c WHERE c.book.id=:id", RdbmsComment.class);
        return query.setParameter("id", bookId).getResultList();
    }

    @Override
    public List<RdbmsComment> getAll() {
        TypedQuery<RdbmsComment> query = em.createQuery("SELECT c FROM RdbmsComment c", RdbmsComment.class);
        return query.getResultList();
    }
}
