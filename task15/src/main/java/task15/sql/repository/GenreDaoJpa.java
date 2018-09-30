package task15.sql.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import task15.sql.domain.RdbmsGenre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
@Repository
public class GenreDaoJpa implements GenreDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(RdbmsGenre genre) {
        em.persist(genre);
    }

    @Override
    public RdbmsGenre update(RdbmsGenre genre) {
        return em.merge(genre);
    }

    @Override
    public void delete(int id) {
        RdbmsGenre genre = getById(id);
        em.remove(genre);
    }

    @Override
    public RdbmsGenre getById(int id) {
        return em.find(RdbmsGenre.class, id);
    }

    @Override
    public List<RdbmsGenre> getAll() {
        TypedQuery<RdbmsGenre> query = em.createQuery("SELECT g FROM RdbmsGenre g", RdbmsGenre.class);
        return query.getResultList();
    }
}
