package unsw.ats.MongoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unsw.ats.entities.Job;
import unsw.ats.repository.JobRepository;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: yousilin
 * Date: 13/05/12
 * Time: 9:07 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    public Job create(Job job) {
        job.setJobId(UUID.randomUUID().toString());
        return jobRepository.save(job);
    }

}
