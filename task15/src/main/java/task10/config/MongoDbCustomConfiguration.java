package task10.config;

import com.mongodb.MongoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
@RequiredArgsConstructor
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class})
@EnableConfigurationProperties(MongoDbProperties.class)
public class MongoDbCustomConfiguration {

    private final MongoDbProperties mongoDbProperties;

    @Bean
    @Primary
    public MongoDbFactory customFactory(final MongoProperties mongo) throws Exception {
        return new SimpleMongoDbFactory(
                new MongoClient(mongo.getHost(), mongo.getPort()),
                mongo.getDatabase()
        );
    }

    @Primary
    @Bean
    public MongoTemplate customMongoTemplate() throws Exception {
        return new MongoTemplate(customFactory(mongoDbProperties.getPrimary()));
    }

}
