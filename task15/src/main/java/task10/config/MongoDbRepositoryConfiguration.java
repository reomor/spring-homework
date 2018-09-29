package task10.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "task10.nosql.repository", mongoTemplateRef = "customMongoTemplate")
public class MongoDbRepositoryConfiguration {
}
