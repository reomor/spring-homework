package task15.nosql.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import task15.nosql.domain.Book;
import task15.nosql.domain.Comment;

import java.util.ArrayList;
import java.util.List;

public class BookRepositoryImpl implements ExtendedBookRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

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
