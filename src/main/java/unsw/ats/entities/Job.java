package unsw.ats.entities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: mendy
 * Date: 5/13/12
 * Time: 4:04 PM
 * To change this template use File | Settings | File Templates.
 */
@Document
public class Job {
    @Id
    private String jobId;

    @DBRef
    private Recuriter recuriter;
    private String jobTitle;
    private String jobDesc;
    private float salary;
    private String location;
    private Calendar closingDate;

    /**
     *  status = 1  job open
     *  status = 0  job closed
     */
    private String status;




    public Recuriter getRecuriter() {
        return recuriter;
    }

    public void setRecuriter(Recuriter recuriter) {
        this.recuriter = recuriter;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Calendar getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Calendar closingDate) {
        this.closingDate = closingDate;
        if (Calendar.getInstance().after(closingDate))
            status = "Closed";
        else
            status = "Open";
    }

    public String getStatus() {
        if (closingDate != null) {
            if (Calendar.getInstance().after(closingDate))
                return "Closed";
        }

        return "Open";
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
