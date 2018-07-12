package task05.service;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestingProcessServiceTest {

    @Autowired
    private TestingProcessService testingProcessService;

    private InputStream systemIn = System.in;

    @After
    public void down() {
        System.setIn(systemIn);
    }

    @Test
    public void test() throws IOException {
        String input = "0\nIvan Ivanov\n";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(byteArrayInputStream);
        task05.model.Test test = testingProcessService.processTest();
        //assertEquals(Locale.ENGLISH, locale);
        byteArrayInputStream.close();
    }

}