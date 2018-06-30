package task02;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import task02.service.QuestionService;
import task02.service.TestingProcessService;

@Configuration
@ComponentScan
@PropertySource("classpath:config.properties")
public class Main {

    @Bean
    public QuestionService questionService(@Value("${question.file}") String questionFileName) {
        return new QuestionService(questionFileName);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/i18n/bundle");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        TestingProcessService testingProcessService = context.getBean(TestingProcessService.class);
        testingProcessService.processTest();
    }
}
