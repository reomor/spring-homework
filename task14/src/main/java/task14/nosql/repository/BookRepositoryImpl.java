package task14.nosql.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import task14.nosql.domain.MongoBook;
import task14.nosql.domain.MongoComment;

import java.util.ArrayList;
import java.util.List;

public class BookRepositoryImpl implements ExtendedBookRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void setComment(String id, MongoComment comment) {
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(id)), new Update().push("comments", comment), MongoBook.class);
    }

    @Override
    public List<MongoComment> getComments(String id) {
        MongoBook book = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), MongoBook.class);
        return (book != null) ? book.getComments() : new ArrayList<>();
    }
}
