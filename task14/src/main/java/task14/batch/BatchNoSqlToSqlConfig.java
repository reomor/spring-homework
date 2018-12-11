package task14.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import task14.nosql.domain.MongoAuthor;
import task14.nosql.domain.MongoBook;
import task14.nosql.domain.MongoComment;
import task14.nosql.domain.MongoGenre;
import task14.sql.domain.RdbmsAuthor;
import task14.sql.domain.RdbmsBook;
import task14.sql.domain.RdbmsComment;
import task14.sql.domain.RdbmsGenre;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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

    private <T> MongoItemReader<T> getReader(Class<T> targetType) {
        MongoItemReader<T> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setSort(new HashMap<String, Sort.Direction>() {{
            put("_id", Sort.Direction.DESC);
        }});
        reader.setTargetType(targetType);
        reader.setQuery("{}");
        return reader;
    }

    @Bean
    public MongoItemReader<MongoAuthor> reader1() {
        return getReader(MongoAuthor.class);
    }

    @Bean
    public MongoItemReader<MongoBook> reader2() {
        return getReader(MongoBook.class);
    }

    @Bean
    public ItemProcessor<MongoAuthor, RdbmsAuthor> processor1() {
        return mongoAuthor -> new RdbmsAuthor(mongoAuthor.getName(), mongoAuthor.getSername(), mongoAuthor.getDateOfBirth(), mongoAuthor.getBiography());
    }

    @Bean
    public ItemProcessor<MongoBook, RdbmsBook> processor2() {
        return mongoBook -> {
            MongoGenre genre = mongoBook.getGenre();
            List<MongoAuthor> mongoAuthors = mongoBook.getAuthors();
            List<MongoComment> mongoComments = mongoBook.getComments();
            List<RdbmsAuthor> authors = mongoAuthors.stream()
                    .map(mongoAuthor -> new RdbmsAuthor(mongoAuthor.getName(), mongoAuthor.getSername(), mongoAuthor.getDateOfBirth(), mongoAuthor.getBiography()))
                    .collect(Collectors.toList());
            List<RdbmsComment> comments = mongoComments.stream()
                    .map(mongoComment -> new RdbmsComment(mongoComment.getCommentBody(), mongoComment.getDate()))
                    .collect(Collectors.toList());
            return new RdbmsBook(
                    mongoBook.getTitle(),
                    new RdbmsGenre(genre.getGenreName(), genre.getGenreDescription()),
                    mongoBook.getIsbn(),
                    mongoBook.getDescription(),
                    authors,
                    comments);
        };
    }

    private <T> JpaItemWriter<T> getWriter(Class<T> clazz) {
        JpaItemWriter<T> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManager.getEntityManagerFactory());
        return writer;
    }

    @Bean
    public  JpaItemWriter<RdbmsAuthor> writer1() {
        return getWriter(RdbmsAuthor.class);
    }

    @Bean
    public  JpaItemWriter<RdbmsBook> writer2() {
        return getWriter(RdbmsBook.class);
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<MongoAuthor, RdbmsAuthor>chunk(3)
                .reader(reader1())
                .processor(processor1())
                .writer(writer1())
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .<MongoBook, RdbmsBook>chunk(3)
                .reader(reader2())
                .processor(processor2())
                .writer(writer2())
                .build();
    }

    @Bean
    public Job noSqlToSqlMigration(JobCompletionNotificationListener listener, Step step1, Step step2) {
        return jobBuilderFactory.get("noSqlToSqlMigration")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step2)
                .end()
                .build();
    }
}
