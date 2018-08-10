package task09.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import task09.domain.BookDocument;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        //InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        //ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class BookDocumentRepositoryAutoConfTest {

    @Autowired
    private BookDocumentRepository repository;

    @Test
    public void shouldCreateBook() {
        BookDocument bookDocument = new BookDocument("NewBook");
        BookDocument savedBook = repository.save(bookDocument);
        assertNotNull(savedBook.getId());
        assertEquals(bookDocument.getTitle(), savedBook.getTitle());
    }
}