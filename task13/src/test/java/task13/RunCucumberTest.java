package task13;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty"},
        tags = "@UpdateProfile",
        snippets = SnippetType.CAMELCASE
)
public class RunCucumberTest {
}
