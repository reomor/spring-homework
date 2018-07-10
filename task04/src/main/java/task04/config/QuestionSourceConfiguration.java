package task04.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import task04.service.QuestionService;

// https://github.com/eugenp/tutorials/blob/master/spring-boot-autoconfiguration/src/main/java/com/baeldung/autoconfiguration/MySQLAutoconfiguration.java
@Configuration
@PropertySource("classpath:application.yml")
public class QuestionSourceConfiguration {

    @Bean
    @ConditionalOnProperty(name = "load-type", havingValue = "csv")
    public QuestionService questionService() {
        return null;
    }

    @Bean
    @ConditionalOnProperty(name = "load-type", havingValue = "yml")
    public QuestionService questionService2() {
        return null;
    }
}
