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

    public static final String INPUT_CHANNEL = "flows.input";
    public static final String FIBONACCI_CALCULATION_CHANNEL = "flows.fibonacci";
    public static final String FIBONACCI_RESULT_CHANNEL = "flows.fibonacci.output";

    @MessagingGateway
    public interface InputGateway {
        @Gateway(requestChannel = FIBONACCI_CALCULATION_CHANNEL, replyChannel = FIBONACCI_RESULT_CHANNEL)
        Integer run(@Payload FibonacciPayload payload);
    }

    @Bean
    //@BridgeFrom("pubSubChannel")
    public MessageChannel fibonacciResultChannel() {
        return MessageChannels.rendezvous(FIBONACCI_RESULT_CHANNEL).datatype(Integer.class).get();
    }

    @Bean
    public IntegrationFlow logFlow() {
        return IntegrationFlows.from(INPUT_CHANNEL)
                .log()
                .channel(
                        MessageChannels.publishSubscribe("pubSubChannel").minSubscribers(0)
                ).routeToRecipients(recipientListRouterSpec -> {
                    recipientListRouterSpec
                            .recipientFlow(flow -> {
                                flow.transform(FibonacciPayload::getFibonacciNumber).channel(FIBONACCI_RESULT_CHANNEL);
                    });
                })
                .get();
    }

    @Bean
    public IntegrationFlow complexFlow() {
        return f -> f
                .channel(FIBONACCI_CALCULATION_CHANNEL)
                .log()
                .<FibonacciPayload, Boolean>route(
                        FibonacciPayload::isLimitIsReached,
                        mapping -> mapping
                                .subFlowMapping(
                                        false,
                                        sf -> sf.<FibonacciPayload, FibonacciPayload>transform(FibonacciPayload::new)
                                                .channel(FIBONACCI_CALCULATION_CHANNEL))
                                .subFlowMapping(
                                        true,
                                        sf -> sf.channel(INPUT_CHANNEL)
                                ));
    }
}
