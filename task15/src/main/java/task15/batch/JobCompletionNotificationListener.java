package task15.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task15.sql.repository.AuthorDao;
import task15.sql.repository.BookDao;
import task15.sql.repository.CommentDao;
import task15.sql.repository.GenreDao;

import java.util.List;

@Slf4j
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private GenreDao genreDao;

    @Autowired
    private CommentDao commentDao;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Before job.");
        checkList("Authors", authorDao.getAll());
        checkList("Books", bookDao.getAll());
        checkList("Genres", genreDao.getAll());
        checkList("Comments", commentDao.getAll());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Job finished!");
            checkList("Authors", authorDao.getAll());
            checkList("Books", bookDao.getAll());
            checkList("Genres", genreDao.getAll());
            checkList("Comments", commentDao.getAll());
        }
    }

    private <T> void checkList(String tableName, List<T> list) {
        log.info("Number of rows in table " + tableName + ": " + list.size());
        list.forEach(System.out::println);
    }
}
