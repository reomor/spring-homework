package task15.batch;

import org.springframework.batch.item.ItemProcessor;
import task15.nosql.domain.MongoAuthor;
import task15.sql.domain.RdbmsAuthor;

public class AuthorNoSqlToSqlProcessor implements ItemProcessor<MongoAuthor, RdbmsAuthor> {
    @Override
    public RdbmsAuthor process(MongoAuthor mongoAuthor) throws Exception {
        return null;
    }
}
