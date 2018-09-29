package task15.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "task15.nosql.repository")
public class MongoDbCustomRepositoryConfiguration {
}
