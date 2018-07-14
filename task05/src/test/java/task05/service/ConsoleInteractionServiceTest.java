package task05.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import task05.model.Question;
import task05.model.User;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Locale;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
public class ConsoleInteractionServiceTest {

    @Autowired
    private ConsoleInteractionService consoleInteractionService;

    @Test
    public void askLocale_shouldNotChange_fromEn() throws IOException {
        BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn("0");
        setFieldViaReflection(consoleInteractionService, "reader", bufferedReader);

        Locale locale = consoleInteractionService.askLocale();
        assertEquals(Locale.ENGLISH, locale);
    }

    @Test
    public void askLocale_shouldChange_toRu() throws IOException {
        BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn("1");
        setFieldViaReflection(consoleInteractionService, "reader", bufferedReader);

        Locale expected = new Locale("ru", "RU");
        Locale actual = consoleInteractionService.askLocale();
        assertEquals(expected, actual);
    }

    @Test
    public void askUserInformation_shouldBeEqual() throws IOException {
        BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn("Ivan Ivanov");
        setFieldViaReflection(consoleInteractionService, "reader", bufferedReader);

        User expected = new User("Ivan", "Ivanov");
        User actual = consoleInteractionService.askUserInformation();
        assertEquals(expected, actual);
    }

    @Test
    public void askUserInformation_shouldBeNull() throws IOException {
        BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn("Ivan Ivanov excess");
        setFieldViaReflection(consoleInteractionService, "reader", bufferedReader);

        User actual = consoleInteractionService.askUserInformation();
        assertEquals(null, actual);
    }

    @Test
    public void askQuestions() throws IOException {
        BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine())
                .thenReturn("1")
                .thenReturn("1");
        setFieldViaReflection(consoleInteractionService, "reader", bufferedReader);

        Integer questionAmount = consoleInteractionService.askQuestions(
                Arrays.asList(
                        new Question("Perque?", Arrays.asList("0 - Uno", "1 - Duo"), 0),
                        new Question("Perque2?", Arrays.asList("0 - Uno", "1 - Duo"), 1)
                )
        );
        assertEquals(2L, questionAmount.longValue());
    }

    private void setFieldViaReflection(Object object, String valueFieldName, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(valueFieldName);
            boolean isAcceseble = field.isAccessible();
            field.setAccessible(true);
            field.set(object, value);
            field.setAccessible(isAcceseble);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}