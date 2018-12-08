package task06.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task06.dao.BookDao;
import task06.dao.CommentDao;
import task06.domain.Book;
import task06.domain.Comment;
import task06.exception.ConsoleReadException;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;
    private final BookDao bookDao;

    @Autowired
    public CommentServiceImpl(CommentDao commentDao, BookDao bookDao) {
        this.commentDao = commentDao;
        this.bookDao = bookDao;
    }

    @Override
    public void create(BufferedReader reader) {
        Comment comment = null;
        boolean valid = false;
        Integer bookId = null;
        Book book;
        do {
            printList(bookDao.getAll());
            System.out.println("Enter book id to add comment:");
            try {
                bookId = Integer.parseInt(reader.readLine());
                valid = true;
            } catch (IOException ignored) { }
        } while (!valid);
        try {
            comment = readComment(reader);
            comment.setBook(bookDao.getById(bookId));
            commentDao.create(comment);
        } catch (IOException e) {
            throw new ConsoleReadException("Error while reading " + Comment.class.getName());
        }
    }

    @Override
    public Comment update(BufferedReader reader) {
        boolean valid = false;
        Integer updateId = null;
        Comment updatedComment;
        while (!valid) {
            try {
                getAll();
                valid = true;
                System.out.println("Enter comment id to update:");
                updateId = Integer.parseInt(reader.readLine());
            } catch (IOException e) {
                valid = false;
            }
        }
        try {
            updatedComment = updateComment(reader, commentDao.getById(updateId));
        } catch (IOException e) {
            throw new ConsoleReadException("Error while updating " + Comment.class.getName());
        }
        return updatedComment;
    }

    @Override
    public void delete(int id) {
        commentDao.delete(id);
    }

    @Override
    public void getById(int id) {
        printObject(commentDao.getById(id));
    }

    @Override
    public void getAll() {
        printList(commentDao.getAll());
    }

    @Override
    public void getByBookId(int bookId) {
        printList(commentDao.getByBookId(bookId));
    }

    private Comment readComment(BufferedReader reader) throws IOException {
        System.out.println("Reading Comment object.\nEnter comment:");
        String comment = reader.readLine();
        return new Comment(null, comment, LocalDateTime.now());
    }

    private Comment updateComment(BufferedReader reader, Comment comment) throws IOException {
        if (comment == null) {
            throw new ConsoleReadException(Comment.class.getName() + " object is null");
        }
        System.out.println("Reading Comment object.\nEnter comment:");
        String commentBody = reader.readLine();
        if (!commentBody.isEmpty()) {
            comment.setBody(commentBody);
        }
        return comment;
    }
}
