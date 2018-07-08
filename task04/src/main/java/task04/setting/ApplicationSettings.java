package task04.setting;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@ConfigurationProperties("application")
public class ApplicationSettings {

    private String fileQuestion;
    private Locale locale;

    public String getFileQuestion() {
        return fileQuestion;
    }

    public void setFileQuestion(String fileQuestion) {
        this.fileQuestion = fileQuestion;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
