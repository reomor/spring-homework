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
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/i18n/bundle");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

    @Bean
    public QuestionService questionService(@Value("${file.questions}") String questionFileName, MessageSource messageSource) {
        return new QuestionService(questionFileName, messageSource);
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        TestingProcessService testingProcessService = context.getBean(TestingProcessService.class);
        testingProcessService.processTest();
    }
}
