package unsw.ats.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import unsw.ats.entities.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: yousilin
 * Date: 13/05/12
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
@Path("/common")
public class Controller {

    @Autowired
    private MongoTemplate mongoTemplate;

    @GET
    @Path("/init")
    @Produces(MediaType.APPLICATION_JSON)
    public Response test() throws ParseException {
        mongoTemplate.dropCollection("job");
        mongoTemplate.dropCollection("test");
        mongoTemplate.dropCollection("application");
        mongoTemplate.dropCollection("recuriter");
        mongoTemplate.dropCollection("applicant");
        mongoTemplate.dropCollection("reviewer");
        mongoTemplate.dropCollection("user");

        Recuriter recuriter = new Recuriter();
        recuriter.setUserId("5e6d21e0-0132-4f3e-a868-7ce0de05f706");
        recuriter.setRecruiterName("StevenY");

        Applicant applicant = new Applicant();
        applicant.setApplicantId("3f9d6286-e131-42fe-a73f-d85f242bdf62");
        applicant.setApplicantName("Mendy");

        Reviewer reviewer = new Reviewer();
        reviewer.setId("b8af6b41-0fda-495a-8deb-af539dddbe90");
        reviewer.setReviewerName("Edison");

        Reviewer Jackson = new Reviewer();
        Jackson.setId("2c1f914d-d932-41ef-b7ab-76e6dac42794");
        Jackson.setReviewerName("Jackson");


        Job jobJava = new Job();
        jobJava.setJobId(UUID.randomUUID().toString());
        jobJava.setJobTitle("jobJava");
        jobJava.setJobDesc("This is a J2EE job");
        DateFormat formatter ;
        Date date ;
        formatter = new SimpleDateFormat("dd-MMM-yy");
        date = (Date)formatter.parse("11-June-12");
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        jobJava.setClosingDate(cal);
        jobJava.setRecuriter(recuriter);

        Job jobObjectiveC = new Job();
        jobObjectiveC.setJobId(UUID.randomUUID().toString());
        jobObjectiveC.setJobTitle("jobObjectiveC");
        jobObjectiveC.setJobDesc("This is a Objective C, iOS job");
        jobObjectiveC.setClosingDate(cal);
        jobObjectiveC.setRecuriter(recuriter);

        Job jobPython = new Job();
        jobPython.setJobId(UUID.randomUUID().toString());
        jobPython.setJobTitle("jobPython");
        jobPython.setJobDesc("This is a python job");
        jobPython.setClosingDate(cal);
        jobPython.setRecuriter(recuriter);

        Application application = new Application();
        application.setApplicationId(UUID.randomUUID().toString());
        application.setApplicant(applicant);
        application.setBriefBio("I am a cute Rabbit and I can coding.");
        application.setStatus("Application Received");
        application.setJob(jobJava);

        // Insert to db
        mongoTemplate.insert(jobJava, "job");
        mongoTemplate.insert(jobObjectiveC, "job");
        mongoTemplate.insert(jobPython, "job");
        mongoTemplate.insert(recuriter, "recuriter");
        mongoTemplate.insert(applicant, "applicant");
        mongoTemplate.insert(reviewer, "reviewer");
        mongoTemplate.insert(Jackson, "reviewer");
        mongoTemplate.insert(application, "application");

        return Response.status(200).entity("Howdy, dude!").build();
    }
}
