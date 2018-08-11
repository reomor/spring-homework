package task09.repository;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import task09.domain.Book;
import task09.domain.Comment;
import task09.domain.Genre;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    public void shouldCreateBook() {
        Genre genre = new Genre("Genre", "Genre");
        Comment comment = new Comment("Like a shit", LocalDateTime.now());
        Comment comment2 = new Comment("Like a shit2", LocalDateTime.now());
        Book expected = new Book("Title", genre, "ISBN-01", "Description", Collections.emptyList(), Arrays.asList(comment, comment2) );
        Book actual = bookRepository.save(expected);
        assertNotNull(actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
    }
}