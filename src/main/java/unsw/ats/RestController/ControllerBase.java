package unsw.ats.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unsw.ats.MongoService.ApplicantService;
import unsw.ats.MongoService.RecuriterService;
import unsw.ats.MongoService.ReviewerService;
import unsw.ats.entities.Applicant;
import unsw.ats.entities.Recuriter;
import unsw.ats.entities.Reviewer;

/**
 * Created with IntelliJ IDEA.
 * User: yousilin
 * Date: 30/05/12
 * Time: 1:30 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ControllerBase {
    @Autowired
    private RecuriterService recuriterService;

    @Autowired
    private ReviewerService reviewerService;

    @Autowired
    private ApplicantService applicantService;
    protected boolean validate(String userId, int type) {
//        return true;
        if ((type & 1) > 0) {
            for (Recuriter r : recuriterService.readAll()) {
                if (r.getUserId().equals(userId))
                    return true;
            }
        }
        if((type & 2) > 0 ){
            for (Reviewer r: reviewerService.readAll()) {
                if(r.getId().equals(userId))
                    return true;
            }
        }
        if((type & 4) > 0){
            for(Applicant a: applicantService.readAll()) {
                if(a.getApplicantId().equals(userId))
                    return true;
            }
        }
        return false;
    }
}
