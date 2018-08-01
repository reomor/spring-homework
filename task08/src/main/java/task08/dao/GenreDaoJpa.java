package task08.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import task08.domain.Genre;

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
    public void create(Genre genre) {
        em.persist(genre);
    }

    @Override
    public Genre update(Genre genre) {
        return em.merge(genre);
    }

    @Override
    public void delete(int id) {
        Genre genre = getById(id);
        em.remove(genre);
    }

    @Override
    public Genre getById(int id) {
        return em.find(Genre.class, id);
    }

    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> query = em.createQuery("SELECT g FROM Genre g", Genre.class);
        return query.getResultList();
    }
}
