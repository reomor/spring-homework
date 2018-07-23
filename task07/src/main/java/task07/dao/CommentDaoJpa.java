package task07.dao;

import org.springframework.stereotype.Repository;
import task07.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CommentDaoJpa implements CommentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Comment comment) {
        em.persist(comment);
    }
}
