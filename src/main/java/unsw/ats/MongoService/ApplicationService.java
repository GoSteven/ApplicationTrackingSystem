package unsw.ats.MongoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unsw.ats.entities.Application;
import unsw.ats.repository.ApplicationRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: mendy
 * Date: 5/23/12
 * Time: 5:09 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;

    public Application create(Application application) {
        application.setApplicationId(UUID.randomUUID().toString());
        return applicationRepository.save(application);
    }

    public Application findById(String applicationId){
        return applicationRepository.findByApplicationId(applicationId);
    }

    public List<Application> readAll() {
        return applicationRepository.findAll();
    }
}
