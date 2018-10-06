package task16.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.dsl.DelayerEndpointSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import task16.domain.FibonacciPayload;

@Configuration
public class Config {

    @MessagingGateway
    public interface InputGateway {
        @Gateway(requestChannel = "in2")
        void run(FibonacciPayload payload);
    }

    @Bean
    public IntegrationFlow sampleFlow() {
        return IntegrationFlows.from("in1").log().get();
    }

    @Bean
    public IntegrationFlow complexFlow() {
        return f -> f.channel("in2")
                .log()
                .<FibonacciPayload, FibonacciPayload>transform(FibonacciPayload::new)
                .delay("normalMessage", (DelayerEndpointSpec e) -> e.defaultDelay(2000))
                .channel("in2");
    }
}
