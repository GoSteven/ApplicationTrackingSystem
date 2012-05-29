package unsw.ats.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created with IntelliJ IDEA.
 * User: mendy
 * Date: 5/13/12
 * Time: 8:36 PM
 * To change this template use File | Settings | File Templates.
 */
@Document
public class User {
    @Id
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /**
     * type = 0 recruiter
     * type = 1 reviewer
     * type = 2 applicant

     */
    private int type;
}
