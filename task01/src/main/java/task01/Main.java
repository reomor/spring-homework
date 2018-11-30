package task01;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import task01.service.TestingProcessService;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        TestingProcessService testingProcessService = context.getBean(TestingProcessService.class);
        testingProcessService.processTest();
    }
}
