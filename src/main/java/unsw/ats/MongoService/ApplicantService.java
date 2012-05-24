package unsw.ats.MongoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unsw.ats.entities.Applicant;
import unsw.ats.repository.ApplicantRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: mendy
 * Date: 5/23/12
 * Time: 3:53 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ApplicantService {
    @Autowired
    private ApplicantRepository applicantRepository;

    public Applicant create(Applicant applicant) {
        applicant.setApplicantId(UUID.randomUUID().toString());
        return applicantRepository.save(applicant);
    }

    public List<Applicant> readAll() {
        return applicantRepository.findAll();
    }

    public Applicant findById(String applicantId) {
        return applicantRepository.findByApplicantId(applicantId);
    }
}
