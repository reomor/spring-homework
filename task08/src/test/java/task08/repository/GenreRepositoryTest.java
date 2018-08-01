package task08.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import task08.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void createGenre_ShouldSuccess_CreateGenre() {
        Genre genre = new Genre(null, "new", "description");
        genreRepository.save(genre);
        assertThat(genre.getId()).isNotNull();
    }

    @Test
    public void updateGenre_ShouldSuccess_UpdateGenre() {
        final String updateString = "Updated";
        Genre expected = new Genre(null, "new", "description");
        genreRepository.save(expected);
        expected.setName(updateString);
        Optional<Genre> genreOptional = genreRepository.findById(expected.getId());
        assertTrue(genreOptional.isPresent());
        assertEquals(updateString, genreOptional.get().getName());
    }

    @Test
    public void deleteGenre_ShouldSuccess_DeleteGenre() {
        Genre genre = new Genre(null, "new", "description");
        genreRepository.save(genre);
        genreRepository.deleteById(genre.getId());
        Optional<Genre> genreOptional = genreRepository.findById(genre.getId());
        assertFalse(genreOptional.isPresent());
    }

    @Test
    public void getGenreById_ShouldSuccess_GetGenreById() {
        Genre expected = new Genre(null, "new", "description");
        genreRepository.save(expected);
        Optional<Genre> genreOptional = genreRepository.findById(expected.getId());
        assertTrue(genreOptional.isPresent());
        assertNotNull(genreOptional.get());
    }

    @Test
    public void getAllGenres_ShouldSuccess_GetAllGenres() {
        Genre expected = new Genre(null, "new", "description");
        genreRepository.save(expected);
        List<Genre> all = genreRepository.findAll();
        assertThat(all).isNotEmpty().contains(expected);
    }
}