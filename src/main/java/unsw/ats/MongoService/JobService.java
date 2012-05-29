package unsw.ats.MongoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unsw.ats.entities.Job;
import unsw.ats.repository.JobRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    public Job read(Job job) {
        return job;
    }

    public List<Job> readAll() {
        return jobRepository.findAll();
    }

    public Job findById(String jobId){
        return jobRepository.findByJobId(jobId);
    }

    public Job update(Job job) {
        Job existingJob = jobRepository.findByJobId(job.getJobId());
        if (existingJob == null) {
            return null;
        }
        existingJob.setJobDesc(job.getJobDesc());
        existingJob.setJobTitle(job.getJobTitle());
        existingJob.setClosingDate(job.getClosingDate());
        existingJob.setLocation(job.getLocation());
        existingJob.setRecuriter(job.getRecuriter());
        return jobRepository.save(existingJob);
    }

    public boolean delete(Job job) {
        Job existingJob = jobRepository.findByJobId(job.getJobId());
        if (existingJob == null) {
            return false;
        }

        jobRepository.delete(existingJob);
        return true;
    }


}
