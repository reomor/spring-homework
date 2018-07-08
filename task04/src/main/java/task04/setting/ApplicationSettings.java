package task04.setting;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application")
public class ApplicationSettings {

    private String fileQuestion;

    public String getFileQuestion() {
        return fileQuestion;
    }

    public void setFileQuestion(String fileQuestion) {
        this.fileQuestion = fileQuestion;
    }
}
