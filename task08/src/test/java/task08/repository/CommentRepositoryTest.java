package task08.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import task08.domain.Book;
import task08.domain.Comment;

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
        Optional<Book> bookOptional = bookRepository.findById(2);
        assertTrue(bookOptional.isPresent());
        Book book = bookOptional.get();
        Comment comment = new Comment(null, "New comment", LocalDateTime.of(2000, Month.MAY, 25, 10, 0), book);
        commentRepository.save(comment);
        assertThat(comment).isNotNull();
    }

    @Test
    public void deleteById() {
    }

    @Test
    public void findById() {
    }

    @Test
    public void findAll() {
    }

    @Test
    public void findCommentsByBookId() {
        final int bookId = 1;
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        assertTrue(bookOptional.isPresent());
        Book book = bookOptional.get();
        Comment comment = new Comment(null, "New comment", LocalDateTime.of(2000, Month.MAY, 25, 10, 0), book);
        commentRepository.save(comment);
        List<Comment> commentsByBookId = commentRepository.findCommentsByBookId(bookId);
        assertThat(commentsByBookId).isNotEmpty().contains(comment);
        for (Comment c : commentsByBookId) {
            assertEquals(new Integer(bookId), c.getBook().getId());
        }
    }
}