package task03.service;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import task03.model.Question;
import task03.model.User;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Locale;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsoleInteractionServiceTest {

    @Autowired
    private ConsoleInteractionService consoleInteractionService;

    private InputStream systemIn = System.in;

    @After
    public void setUp() {
        System.setIn(systemIn);
    }

    @Test
    public void askLocale_shouldNotChange_fromEn() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("0".getBytes());
        System.setIn(byteArrayInputStream);
        Locale locale = consoleInteractionService.askLocale();
        assertEquals(Locale.ENGLISH, locale);
        byteArrayInputStream.close();
    }

    @Test
    public void askLocale_shouldChange_toRu() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("1".getBytes());
        System.setIn(byteArrayInputStream);
        Locale expected = new Locale("ru", "RU");
        Locale actual = consoleInteractionService.askLocale();
        assertEquals(expected, actual);
        byteArrayInputStream.close();
    }

    @Test
    public void askUserInformation_shouldBeEqual() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("Ivan Ivanov".getBytes());
        System.setIn(byteArrayInputStream);
        User expected = new User("Ivan", "Ivanov");
        User actual = consoleInteractionService.askUserInformation();
        assertEquals(expected, actual);
        byteArrayInputStream.close();
    }

    @Test
    public void askUserInformation_shouldBeNull() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("Ivan".getBytes());
        System.setIn(byteArrayInputStream);
        User actual = consoleInteractionService.askUserInformation();
        assertEquals(null, actual);
        byteArrayInputStream.close();
    }

    @Test
    public void askQuestions() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("0\n1\n".getBytes());
        System.setIn(byteArrayInputStream);
        Integer questionAmount = consoleInteractionService.askQuestions(
                Arrays.asList(
                        new Question("Perque?", Arrays.asList("0 - Uno", "1 - Duo"), 0),
                        new Question("Perque2?", Arrays.asList("0 - Uno", "1 - Duo"), 1)
                )
        );
        assertEquals(2L, questionAmount.longValue());
        byteArrayInputStream.close();
    }
}