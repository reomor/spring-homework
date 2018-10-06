package task16.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.BridgeFrom;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import task16.domain.FibonacciPayload;

import java.util.concurrent.Executor;

@Configuration
public class Config {

    @MessagingGateway
    public interface InputGateway {
        @Gateway(requestChannel = "in2", replyChannel = "fibonacciResultChannel")
        Integer run(@Payload FibonacciPayload payload);
    }

    @Bean
    //@BridgeFrom("pubSubChannel")
    public MessageChannel fibonacciResultChannel() {
        return MessageChannels.rendezvous().datatype(Integer.class).get();
    }

    @Bean
    public IntegrationFlow logFlow() {
        return IntegrationFlows.from("in1")
                .log()
                .channel(
                        MessageChannels.publishSubscribe("pubSubChannel").minSubscribers(0)
                ).routeToRecipients(recipientListRouterSpec -> {
                    recipientListRouterSpec
                            .recipientFlow(flow -> {
                                flow.transform(FibonacciPayload::getFibonacciNumber).channel("fibonacciResultChannel");
                    });
                })
                .get();
    }

    @Bean
    public IntegrationFlow complexFlow() {
        return f -> f
                .channel("in2")
                .log()
                .<FibonacciPayload, Boolean>route(
                        FibonacciPayload::isLimitIsReached,
                        mapping -> mapping
                                .subFlowMapping(
                                        false,
                                        sf -> sf.<FibonacciPayload, FibonacciPayload>transform(FibonacciPayload::new)
                                                .channel("in2"))
                                .subFlowMapping(
                                        true,
                                        sf -> sf.channel("in1")
                                ));
    }
}
