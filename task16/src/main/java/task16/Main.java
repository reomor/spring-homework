package task16;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import task16.config.Config;
import task16.domain.FibonacciPayload;

import java.sql.SQLException;

@SpringBootApplication
@EnableIntegration
@IntegrationComponentScan
public class Main implements CommandLineRunner {

    private final Config.InputGateway inputGateway;

    @Autowired
    public Main(Config.InputGateway inputGateway) {
        this.inputGateway = inputGateway;
    }

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        final Integer LIMIT = 10;
        Integer fibonacci = inputGateway.run(new FibonacciPayload(LIMIT));
        System.out.println(LIMIT + "th Fibonacci number is: " + fibonacci);
    }
}
