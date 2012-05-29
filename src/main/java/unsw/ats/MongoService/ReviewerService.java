package unsw.ats.MongoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unsw.ats.entities.Reviewer;
import unsw.ats.repository.ReviewerRepository;

import java.rmi.server.UID;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: mendy
 * Date: 5/23/12
 * Time: 3:31 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ReviewerService {
    @Autowired
    private ReviewerRepository reviewerRepository;

    public Reviewer create(Reviewer reviewer) {
        reviewer.setId(UUID.randomUUID().toString());
        return reviewerRepository.save(reviewer);
    }

    public Reviewer findById(String reviewerId){
        return reviewerRepository.findById(reviewerId);

    }
    public List<Reviewer> readAll() {
        return reviewerRepository.findAll();
    }
}
