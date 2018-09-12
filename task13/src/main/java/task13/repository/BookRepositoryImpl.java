package task13.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import task13.domain.Book;
import task13.domain.Comment;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookRepositoryImpl implements ExtendedBookRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public BookRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void setComment(String id, Comment comment) {
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(id)), new Update().push("comments", comment), Book.class);
    }

    @Override
    public List<Comment> getComments(String id) {
        Book book = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), Book.class);
        return (book != null) ? book.getComments() : new ArrayList<>();
    }
}
