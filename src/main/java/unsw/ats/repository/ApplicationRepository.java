package unsw.ats.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import unsw.ats.entities.Application;
import unsw.ats.entities.Recuriter;

/**
 * Created with IntelliJ IDEA.
 * User: yousilin
 * Date: 14/05/12
 * Time: 9:49 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ApplicationRepository extends MongoRepository<Application, String> {
    Application findByApplicationId(String applicationId);
}
