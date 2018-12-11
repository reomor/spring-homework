package task14.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "task14.nosql.repository")
public class MongoDbCustomRepositoryConfiguration {
}
