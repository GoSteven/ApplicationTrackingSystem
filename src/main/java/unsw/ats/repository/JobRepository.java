package unsw.ats.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import unsw.ats.entities.Job;

/**
 * Created with IntelliJ IDEA.
 * User: yousilin
 * Date: 13/05/12
 * Time: 9:05 PM
 * To change this template use File | Settings | File Templates.
 */
public interface JobRepository extends MongoRepository <Job, String> {
}
