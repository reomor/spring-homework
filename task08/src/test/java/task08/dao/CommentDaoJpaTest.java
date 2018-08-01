package task08.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import task08.domain.Book;
import task08.domain.Comment;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class CommentDaoJpaTest extends AbstractTest {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private BookDao bookDao;

    @Test
    public void createComment_ShouldSuccess_CreateNewCommentWithoutBook() {
        Comment expected = new Comment(null, "Test comment", LocalDateTime.now());
        commentDao.create(expected);
        Comment actual = commentDao.getById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void createComment_ShouldSuccess_CreateNewCommentWitBook() {
        Book book = bookDao.getById(1);
        Comment expected = new Comment(null, "Test comment", LocalDateTime.now(), book);
        commentDao.create(expected);
        Comment actual = commentDao.getById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void deleteComment_ShouldSuccess_DeleteComment() {
        final int deleteId = 3;
        int size = commentDao.getAll().size();
        commentDao.delete(deleteId);
        assertEquals(size - 1, commentDao.getAll().size());
        Comment expected = commentDao.getById(deleteId);
        assertNull(expected);
    }

    @Test
    public void getCommentById_ShouldSuccess_ReturnCommentById() {
        final int getId = 2;
        Comment actual = commentDao.getById(getId);
        assertEquals("Comment 2", actual.getBody());
    }

    @Test
    public void getAllComments_ShouldSuccess_ReturnListOfAllComment() {
        List<Comment> all = commentDao.getAll();
        assertThat(all).isNotEmpty();
    }

    @Test
    public void getAllCommentsByBookId_ShouldSuccess_ReturnListOfBookComments() {
        List<Comment> all = commentDao.getByBookId(1);
        assertThat(all).isNotEmpty();
    }

}