package task16.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.store.SimpleMessageStore;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Payload;
import task16.domain.FibonacciPayload;
import task16.domain.Rabbit;

@Configuration
public class Config {

    private static final String INPUT_CHANNEL = "flows.input";
    private static final String FIBONACCI_CALCULATION_CHANNEL = "flows.fibonacci";
    private static final String FIBONACCI_RESULT_CHANNEL = "flows.fibonacci.output";
    private static final String PUBSUB_CHANNEL = "flows.pubsub";
    private static final String QUEUE_CHANNEL = "flows.queue";
    private static final String RABBITS_QUEUE_CHANNEL = "flows.rabbits.queue";

    @MessagingGateway
    public interface InputGateway {

        @Gateway(requestChannel = FIBONACCI_CALCULATION_CHANNEL, replyChannel = FIBONACCI_RESULT_CHANNEL)
        Integer run(@Payload FibonacciPayload payload);
    }

    @Bean
    //@BridgeFrom(value = PUBSUB_CHANNEL) //, poller = @Poller(fixedDelay = "1000"))
    public MessageChannel queueChannel() {
        return MessageChannels.queue(QUEUE_CHANNEL).datatype(FibonacciPayload.class).get();
    }

    @Bean
    public MessageChannel pubSubChannel() {
        return MessageChannels.publishSubscribe(PUBSUB_CHANNEL).minSubscribers(1).get();
    }

    @Bean
    public MessageChannel rabbitsQueueChannel() {
        return MessageChannels.queue(RABBITS_QUEUE_CHANNEL, 10).datatype(Rabbit.class).get();
    }

    @Bean
    public IntegrationFlow rabbitsFlow() {
        return IntegrationFlows.from(RABBITS_QUEUE_CHANNEL)
                //.log()
                .aggregate(aggregatorSpec -> aggregatorSpec
                        .expireGroupsUponCompletion(true)
                        .correlationExpression("#this.payload.correlationId")
                        .sendPartialResultOnExpiry(true)
                        .releaseExpression("#this.size() == 7")
                        .messageStore(new SimpleMessageStore(100))
                        .groupTimeoutExpression("size() ge 2 ? 1000 : -1")
                )
                .log(LoggingHandler.Level.WARN)
                .channel("nullChannel")
                .get();
    }

    @Bean
    public IntegrationFlow pubSubToQueueFlow() {
        return IntegrationFlows.from(PUBSUB_CHANNEL)
                .channel(QUEUE_CHANNEL)
                .get();
    }

    @Bean
    public IntegrationFlow rabbitServiceActivator() {
        return IntegrationFlows.from(QUEUE_CHANNEL)
                .handle("rabbitService", "hornyRabbits") // e -> e.poller(Pollers.fixedDelay(1000))) //, e -> e1.poller(Pollers.fixedDelay(1000)))
                .split()
                .channel(RABBITS_QUEUE_CHANNEL)
                .get();
    }

    @Bean
    public MessageChannel fibonacciResultChannel() {
        return MessageChannels.rendezvous(FIBONACCI_RESULT_CHANNEL).datatype(Integer.class).get();
    }

    @Bean
    public IntegrationFlow logFlow() {
        return IntegrationFlows.from(INPUT_CHANNEL)
                .routeToRecipients(recipientListRouterSpec -> {
                    recipientListRouterSpec
                            .recipient(PUBSUB_CHANNEL)
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
                //.log()
                .<FibonacciPayload, Boolean>route(
                        FibonacciPayload::isLimitIsReached,
                        mapping -> mapping
                                .subFlowMapping(
                                        false,
                                        sf -> sf.<FibonacciPayload, FibonacciPayload>transform(FibonacciPayload::new)
                                                .channel(FIBONACCI_CALCULATION_CHANNEL)
                                )
                                .subFlowMapping(
                                        true,
                                        sf -> sf.channel(INPUT_CHANNEL)
                                ));
    }
}
