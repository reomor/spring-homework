package task09.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import task09.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends CrudRepository<Comment, Integer> {
    @Override
    Comment save(Comment comment);

    @Override
    void deleteById(Integer id);

    @Override
    Optional<Comment> findById(Integer id);

    @Override
    List<Comment> findAll();

    @Query(value = "SELECT c FROM Comment c WHERE c.book.id = :id")
    List<Comment> findCommentsByBookId(@Param("id") int bookId);
}
