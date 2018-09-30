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
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");
            System.out.println(authorDao.getAll().size());
            System.out.println(bookDao.getAll().size());
            System.out.println(genreDao.getAll().size());
            System.out.println(commentDao.getAll().size());
        }
    }
}
