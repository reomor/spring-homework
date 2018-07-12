package task05.setting;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationSettingsTest {
/*
    @Configuration
    static class ApplicationSettingsContextConfiguration {
        @Bean
        public ApplicationSettings applicationSettings() {
            return new ApplicationSettings();
        }
    }
//*/
    @Autowired
    private ApplicationSettings applicationSettings;

    @Test
    public void getSettings() {
        Locale locale = applicationSettings.getLocale();
        assertEquals(Locale.ENGLISH, locale);
        String fileQuestion = applicationSettings.getFileQuestion();
        assertEquals("questions.csv", fileQuestion);
        List<ApplicationSettings.Question> questions = applicationSettings.getQuestions();
        assertEquals(5, questions.size());
    }
}