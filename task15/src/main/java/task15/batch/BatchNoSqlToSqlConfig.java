package task15.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import task15.nosql.domain.MongoAuthor;
import task15.sql.domain.RdbmsAuthor;

import javax.persistence.EntityManager;
import java.util.HashMap;

@Configuration
@EnableBatchProcessing
public class BatchNoSqlToSqlConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private EntityManager entityManager;

    @Bean
    public MongoItemReader<MongoAuthor> reader() {
        MongoItemReader<MongoAuthor> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setSort(new HashMap<String, Sort.Direction>() {{
            put("_id", Sort.Direction.DESC);
        }});
        reader.setTargetType(MongoAuthor.class);
        reader.setQuery("{}");
        return reader;
    }

    @Bean
    public AuthorNoSqlToSqlProcessor processor() {
        return new AuthorNoSqlToSqlProcessor();
    }

    @Bean
    public JpaItemWriter<RdbmsAuthor> writer() {
        JpaItemWriter<RdbmsAuthor> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManager.getEntityManagerFactory());
        return writer;
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<MongoAuthor, RdbmsAuthor>chunk(3)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job noSqlToSqlMigration(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("noSqlToSqlMigration")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }
}
