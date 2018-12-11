package task07.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import task07.domain.Book;
import task07.domain.Comment;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CommentRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void saveComment_ShouldSuccess_SaveNewComment() {
        Book book = bookRepository.findById(2).orElseThrow(() -> new RuntimeException("No book in repo"));
        Comment comment = new Comment(null, "New comment", LocalDateTime.of(2000, Month.MAY, 25, 10, 0), book);
        commentRepository.save(comment);
        assertThat(comment.getId()).isNotNull();
    }

    @Test
    public void updateComment_ShouldSuccess_UpdateComment() {
        final String body = "Updated body";
        Book book = bookRepository.findById(2).orElseThrow(() -> new RuntimeException("No book in repo"));
        Comment comment = new Comment(null, "New comment", LocalDateTime.of(2000, Month.MAY, 25, 10, 0), book);
        commentRepository.save(comment);
        comment.setBody(body);
        Optional<Comment> commentOptional = commentRepository.findById(comment.getId());
        assertTrue(commentOptional.isPresent());
        assertNotNull(commentOptional.get());
    }

    @Test
    public void deleteComment_ShouldSuccess_DeleteCommentById() {
        Book book = bookRepository.findById(2).orElseThrow(() -> new RuntimeException("No book in repo"));
        Comment comment = new Comment(null, "New comment", LocalDateTime.of(2000, Month.MAY, 25, 10, 0), book);
        commentRepository.save(comment);
        commentRepository.deleteById(comment.getId());
        Optional<Comment> commentOptional = commentRepository.findById(comment.getId());
        assertFalse(commentOptional.isPresent());
    }

    @Test
    public void getComment_ShouldSuccess_GetCommentById() {
        Book book = bookRepository.findById(2).orElseThrow(() -> new RuntimeException("No book in repo"));
        Comment comment = new Comment(null, "New comment", LocalDateTime.of(2000, Month.MAY, 25, 10, 0), book);
        commentRepository.save(comment);
        Optional<Comment> commentOptional = commentRepository.findById(comment.getId());
        assertTrue(commentOptional.isPresent());
        assertNotNull(commentOptional.get());
    }

    @Test
    public void getComments_ShouldSuccess_GetAllComments() {
        final int bookId = 1;
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("No book in repo"));
        Comment comment = new Comment(null, "New comment", LocalDateTime.of(2000, Month.MAY, 25, 10, 0), book);
        commentRepository.save(comment);
        List<Comment> all = commentRepository.findAll();
        assertThat(all).isNotEmpty().contains(comment);
    }

    @Test
    public void getComments_ShouldSuccess_GetCommentsByBookId() {
        final int bookId = 1;
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("No book in repo"));
        Comment comment = new Comment(null, "New comment", LocalDateTime.of(2000, Month.MAY, 25, 10, 0), book);
        commentRepository.save(comment);
        List<Comment> commentsByBookId = commentRepository.findCommentsByBookId(bookId);
        assertThat(commentsByBookId).isNotEmpty().contains(comment);
        for (Comment c : commentsByBookId) {
            assertEquals(new Integer(bookId), c.getBook().getId());
        }
    }
}