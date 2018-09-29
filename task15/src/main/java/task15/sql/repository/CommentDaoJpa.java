package task15.sql.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import task15.sql.domain.Comment;

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
    public void create(Comment comment) {
        em.persist(comment);
    }

    @Override
    public void delete(int id) {
        Comment comment = getById(id);
        em.remove(comment);
    }

    @Override
    public Comment getById(int id) {
        return em.find(Comment.class, id);
    }

    @Override
    public List<Comment> getByBookId(int bookId) {
        TypedQuery<Comment> query = em.createQuery("SELECT c FROM Comment c WHERE c.book.id=:id", Comment.class);
        return query.setParameter("id", bookId).getResultList();
    }

    @Override
    public List<Comment> getAll() {
        TypedQuery<Comment> query = em.createQuery("SELECT c FROM Comment c", Comment.class);
        return query.getResultList();
    }
}
