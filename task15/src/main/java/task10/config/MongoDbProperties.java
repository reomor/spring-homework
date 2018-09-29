package task10.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "nosql")
public class MongoDbProperties {
    MongoProperties primary = new MongoProperties();
}
