package task17.service.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task17.domain.Book;
import task17.domain.Comment;
import task17.exception.ConsoleReadException;
import task17.repository.BookRepository;
import task17.repository.CommentRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class CommentRepositoryConsoleServiceImpl implements CommentRepositoryConsoleService {

    private final CommentRepository repository;
    private final BookRepository bookRepository;

    @Autowired
    public CommentRepositoryConsoleServiceImpl(CommentRepository repository, BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void create(BufferedReader reader) {
        Comment comment = null;
        boolean valid = false;
        Integer bookId = null;
        Book book;
        do {
            printList(bookRepository.findAll());
            System.out.println("Enter book id to add comment:");
            try {
                bookId = Integer.parseInt(reader.readLine());
                valid = true;
            } catch (IOException ignored) { }
        } while (!valid);
        try {
            comment = readComment(reader);
            comment.setBook(bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("No book in repo")));
            repository.save(comment);
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
            updatedComment = updateComment(reader, repository.findById(updateId).orElseThrow(() -> new RuntimeException("No comment in repo")));
        } catch (IOException e) {
            throw new ConsoleReadException("Error while updating " + Comment.class.getName());
        }
        return updatedComment;
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Override
    public void getById(int id) {
        printObject(repository.findById(id));
    }

    @Override
    public void getAll() {
        printList(repository.findAll());
    }

    @Override
    public void getByBookId(int bookId) {
        printList(repository.findCommentsByBookId(bookId));
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
