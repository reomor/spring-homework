package task04.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import task04.service.CSVQuestionService;
import task04.service.QuestionService;
import task04.service.YMLQuestionService;
import task04.setting.ApplicationSettings;

// https://github.com/eugenp/tutorials/blob/master/spring-boot-autoconfiguration/src/main/java/com/baeldung/autoconfiguration/MySQLAutoconfiguration.java
@Configuration
@PropertySource("classpath:application.yml")
public class QuestionSourceConfiguration {

    @Bean
    @ConditionalOnProperty(name = "load-type", havingValue = "csv")
    public QuestionService questionService(ApplicationSettings applicationSettings, MessageSource messageSource) {
        return new CSVQuestionService(applicationSettings, messageSource);
    }

    @Bean
    @ConditionalOnProperty(name = "load-type", havingValue = "yml")
    public QuestionService questionService2(ApplicationSettings applicationSettings, MessageSource messageSource) {
        return new YMLQuestionService(applicationSettings, messageSource);
    }
}
