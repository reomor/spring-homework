package task14;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

// https://medium.com/agile-vision/cucumber-bdd-part-2-creating-a-sample-java-project-with-cucumber-testng-and-maven-127a1053c180
// https://github.com/cucumber/cucumber-jvm/tree/master/examples/java-calculator/src/test/java/cucumber/examples/java/calculator
// https://github.com/eugenp/tutorials/blob/master/testing-modules/rest-testing/src/test/resources/karate/user.feature
// https://stackoverflow.com/questions/23564938/can-i-use-spring-to-autowire-controller-in-cucumber-test/28763058

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty"},
        tags = "@restauthors",
        //glue = {"task14.test.stepdefs"},
        snippets = SnippetType.CAMELCASE
)
public class RestCucumberTest {
}
