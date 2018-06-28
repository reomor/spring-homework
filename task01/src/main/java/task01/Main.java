package task01;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import task01.service.QuestionService;
import task01.utils.Utils;

import java.io.FileNotFoundException;


public class Main {

    public static void main(String[] args) {
        //ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        QuestionService questionService = new QuestionService();
        try {
            questionService.loadQuestion();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Utils.readCSVInQuestions();
        //PersonService service = context.getBean(PersonService.class);
        //Person ivan = service.getByName("Ivan");
        //System.out.println("name: " + ivan.getName() + " age: " + ivan.getAge());
    }
}
