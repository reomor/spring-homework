package task16.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

@Configuration
public class Config {

    @Bean
    public IntegrationFlow sampleFlow() {
        return IntegrationFlows.from("in").log().get();
    }

    @MessagingGateway
    public interface InputGateway {
        @Gateway(requestChannel = "in")
        void run(String payload);
    }
}
