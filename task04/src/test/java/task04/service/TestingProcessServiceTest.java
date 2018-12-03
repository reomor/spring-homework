package task04.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.lang.reflect.Field;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
public class TestingProcessServiceTest {

    @Autowired
    private TestingProcessService testingProcessService;

    @Test
    public void testProcessService_shouldSuccess_withThreeRightAnswers() throws IOException {
        try {
            BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
            when(bufferedReader.readLine())
                    .thenReturn("0")
                    .thenReturn("Ivan Ivanov")
                    .thenReturn("1")
                    .thenReturn("1")
                    .thenReturn("1")
                    .thenReturn("1")
                    .thenReturn("1");

            Field consoleInteractionServiceField = testingProcessService.getClass().getDeclaredField("consoleInteractionService");
            boolean isAccesebleConsoleInteractionServiceField = consoleInteractionServiceField.isAccessible();
            consoleInteractionServiceField.setAccessible(true);
            Object consoleInteractionService = consoleInteractionServiceField.get(testingProcessService);
            Field readerField = consoleInteractionServiceField.getType().getDeclaredField("reader");
            boolean isAcceseble = readerField.isAccessible();
            readerField.setAccessible(true);
            readerField.set(consoleInteractionService, bufferedReader);
            readerField.setAccessible(isAcceseble);
            consoleInteractionServiceField.setAccessible(isAccesebleConsoleInteractionServiceField);

            task04.model.Test test = testingProcessService.processTest();

            assertEquals(3, test.getTestResult().getGoodAnswersAmount());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}