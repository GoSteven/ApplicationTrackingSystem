package unsw.ats.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import unsw.ats.entities.Reviewer;

/**
 * Created with IntelliJ IDEA.
 * User: yousilin
 * Date: 14/05/12
 * Time: 9:49 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ReviewerRepository extends MongoRepository<Reviewer, String> {
    Reviewer findById(String reviewerId);

}
