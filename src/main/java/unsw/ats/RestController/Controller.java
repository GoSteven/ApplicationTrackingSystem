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

        Recuriter recuriter1 = new Recuriter();
        recuriter1.setUserId("5e6d21e0-0132-4f3e-a868-7ce0de05f706");
        recuriter1.setRecruiterName("Amira Ahmed");

        Recuriter recuriter2 = new Recuriter();
        recuriter2.setUserId("1h6d21e0-0132-4f3e-a868-7ce0de05f706");
        recuriter2.setRecruiterName("James Gong");

        Applicant applicant1 = new Applicant();
        applicant1.setApplicantId("3f9d6286-e131-42fe-a73f-d85f242bdf62");
        applicant1.setApplicantName("Pop Zheng");

        Applicant applicant2 = new Applicant();
        applicant2.setApplicantId("3f9d6286-e131-42fe-a73f-d85f242bdf62");
        applicant2.setApplicantName("Steven You");

        Applicant applicant3 = new Applicant();
        applicant3.setApplicantId("3f9d6286-e131-42fe-a73f-d85f242bdf62");
        applicant3.setApplicantName("Frank Guo");

        Applicant applicant4 = new Applicant();
        applicant4.setApplicantId("3f9d6286-e131-42fe-a73f-d85f242bdf62");
        applicant4.setApplicantName("Sheldon Rong");

        Reviewer reviewer = new Reviewer();
        reviewer.setId("b8af6b41-0fda-495a-8deb-af539dddbe90");
        reviewer.setReviewerName("Edison");

        Reviewer Jackson = new Reviewer();
        Jackson.setId("2c1f914d-d932-41ef-b7ab-76e6dac42794");
        Jackson.setReviewerName("Jackson");

        Reviewer reviewer1 = new Reviewer();
        reviewer1.setId("g43f6b41-0fda-495a-8deb-af539dddbe90");
        reviewer1.setReviewerName("Sabi You");

        Job jobJava = new Job();
        jobJava.setJobId(UUID.randomUUID().toString());
        jobJava.setJobTitle("Web developer");
        jobJava.setJobDesc("Our team have 2 great online application projects which would last for a year. We need extra web\n" +
                "devs to join the team. The projects will focus on sports area for resource sharing and statistics.\n" +
                "The customers are the top clubs (Manchester United, the whole NBA league, etc) all over the world.");
        DateFormat formatter ;
        Date date ;
        formatter = new SimpleDateFormat("dd-MMM-yy");
        date = (Date)formatter.parse("30-June-12");
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        jobJava.setClosingDate(cal);
        jobJava.setRecuriter(recuriter1);

        Job jobObjectiveC = new Job();
        jobObjectiveC.setJobId(UUID.randomUUID().toString());
        jobObjectiveC.setJobTitle("jobObjectiveC");
        jobObjectiveC.setJobDesc("This is a Objective C, iOS job");
        jobObjectiveC.setClosingDate(cal);
        jobObjectiveC.setRecuriter(recuriter1);

        Job jobPython = new Job();
        jobPython.setJobId(UUID.randomUUID().toString());
        jobPython.setJobTitle("Passion Projects Pty Ltd Web Developer");
        jobPython.setJobDesc("Development & maintenance of our website and on-line presence.\n" +
                "Web content management");
        jobPython.setClosingDate(cal);
        jobPython.setRecuriter(recuriter1);

        Job job3 = new Job();
        job3.setJobId(UUID.randomUUID().toString());
        job3.setJobTitle("Paid Internship on Implementation of the Toolbox Server APIs");
        job3.setJobDesc("A 6-month internship position is available in the Program Analysis group within Oracle Labs,\n" +
                "Brisbane, Australia. The successful candidate must have demonstrable programming skills in the Java\n" +
                "language. ");
        job3.setClosingDate(cal);
        job3.setRecuriter(recuriter1);

        Job job4 = new Job();
        job4.setJobId(UUID.randomUUID().toString());
        job4.setJobTitle("Secondary Mathematics Tutor");
        job4.setJobDesc("The tutoring position is held at MCYAS ( Muslim Cultural and Youth Association Sydney) located in\n" +
                "Minchinbury. It is a centre that provides teaching in quran, arabic and islamic studies as well as\n" +
                "secondary and primary mathematics and english.");
        job4.setClosingDate(cal);
        job4.setRecuriter(recuriter1);

        Job job5 = new Job();
        job5.setJobId(UUID.randomUUID().toString());
        job5.setJobTitle("Analyst Training Program");
        job5.setJobDesc("We're searching for the best and brightest final year technology students to join our Global\n" +
                "Technology (GT) Analyst Training Program in our Production team based in Sydney.");
        job5.setClosingDate(cal);
        job5.setRecuriter(recuriter2);

        Job job6 = new Job();
        job6.setJobId(UUID.randomUUID().toString());
        job6.setJobTitle("Technical Co-founder");
        job6.setJobDesc("TYTBNSMSU is disrupting services with mobile. We are founded on the basis of sexy product, simple\n" +
                "design and intellectual honesty. Your approach to problem solving is creative, elegant and fun.");
        job6.setClosingDate(cal);
        job6.setRecuriter(recuriter2);


        Application application1 = new Application();
        application1.setApplicationId(UUID.randomUUID().toString());
        application1.setApplicant(applicant1);
        application1.setBriefBio("I have successfully designed, developed, and supported live use applications.");
        application1.setStatus("Application Received");
        application1.setJob(jobJava);
        application1.setReviewer1(reviewer);
        application1.setReviewer2(Jackson);


        Application application2 = new Application();
        application2.setApplicationId(UUID.randomUUID().toString());
        application2.setApplicant(applicant2);
        application2.setBriefBio("I strive for continued excellence.");
        application2.setStatus("Application Received");
        application2.setJob(jobJava);
        application1.setReviewer1(reviewer1);
        application1.setReviewer2(Jackson);

        Application application3 = new Application();
        application3.setApplicationId(UUID.randomUUID().toString());
        application3.setApplicant(applicant3);
        application3.setBriefBio("I provide exceptional contributions to customer service for all customers");
        application3.setStatus("Application Received");
        application3.setJob(jobJava);
        application1.setReviewer1(reviewer);
        application1.setReviewer2(reviewer1);

        Application application4 = new Application();
        application4.setApplicationId(UUID.randomUUID().toString());
        application4.setApplicant(applicant4);
        application4.setBriefBio("With a BS degree in Computer Programming, I have a full understanding of the full life cycle of a software development project. I also have experience in learning and excelling at new technologies as needed.");
        application4.setStatus("Application Received");
        application4.setJob(jobJava);
        application1.setReviewer1(reviewer);
        application1.setReviewer2(Jackson);

        Application application5 = new Application();
        application5.setApplicationId(UUID.randomUUID().toString());
        application5.setApplicant(applicant1);
        application5.setBriefBio("I was very interested in your advertisement as I am looking for an international opportunity.");
        application5.setStatus("Application Received");
        application5.setJob(jobObjectiveC);
        application1.setReviewer1(reviewer1);
        application1.setReviewer2(Jackson);

        Application application6 = new Application();
        application6.setApplicationId(UUID.randomUUID().toString());
        application6.setApplicant(applicant2);
        application6.setBriefBio("I have included my presentation in the attached CV.");
        application6.setStatus("Application Received");
        application6.setJob(jobObjectiveC);
        application1.setReviewer1(reviewer1);
        application1.setReviewer2(reviewer);

        Application application7 = new Application();
        application7.setApplicationId(UUID.randomUUID().toString());
        application7.setApplicant(applicant4);
        application7.setBriefBio("Established international musician with homes in London, Paris, Vienna and Tokyo seeks \n" +
                "multilingual and highly organised personal secretary with a background in classical music.");
        application7.setStatus("Application Received");
        application7.setJob(jobObjectiveC);
        application1.setReviewer1(reviewer);
        application1.setReviewer2(Jackson);

        Application application8 = new Application();
        application8.setApplicationId(UUID.randomUUID().toString());
        application8.setApplicant(applicant2);
        application8.setBriefBio("Languages: French (native) & English essential.");
        application8.setStatus("Application Received");
        application8.setJob(jobPython);
        application1.setReviewer1(reviewer);
        application1.setReviewer2(Jackson);

        Application application9 = new Application();
        application9.setApplicationId(UUID.randomUUID().toString());
        application9.setApplicant(applicant3);
        application9.setBriefBio("Experience in classical music management.");
        application9.setStatus("Application Received");
        application9.setJob(jobPython);
        application1.setReviewer1(reviewer1);
        application1.setReviewer2(Jackson);

        Application application10 = new Application();
        application10.setApplicationId(UUID.randomUUID().toString());
        application10.setApplicant(applicant1);
        application10.setBriefBio("Organising the imminent move into an apartment in Paris.");
        application10.setStatus("Application Received");
        application10.setJob(job3);
        application1.setReviewer1(reviewer1);
        application1.setReviewer2(reviewer);

        Application application11 = new Application();
        application11.setApplicationId(UUID.randomUUID().toString());
        application11.setApplicant(applicant1);
        application11.setBriefBio("Managing properties in Paris, London and Vienna.");
        application11.setStatus("Application Received");
        application11.setJob(job4);
        application1.setReviewer1(reviewer);
        application1.setReviewer2(Jackson);

        Application application12 = new Application();
        application12.setApplicationId(UUID.randomUUID().toString());
        application12.setApplicant(applicant3);
        application12.setBriefBio("Excellent administration and organisation skills.");
        application12.setStatus("Application Received");
        application12.setJob(job5);
        application1.setReviewer1(reviewer1);
        application1.setReviewer2(Jackson);

        Application application13 = new Application();
        application13.setApplicationId(UUID.randomUUID().toString());
        application13.setApplicant(applicant2);
        application13.setBriefBio("Self-motivated & pro-active.");
        application13.setStatus("Application Received");
        application13.setJob(job6);
        application1.setReviewer1(reviewer);
        application1.setReviewer2(reviewer1);

        Application application14 = new Application();
        application14.setApplicationId(UUID.randomUUID().toString());
        application14.setApplicant(applicant4);
        application14.setBriefBio("Excellent people and communication skills.");
        application14.setStatus("Application Received");
        application14.setJob(job4);
        application1.setReviewer1(reviewer);
        application1.setReviewer2(Jackson);

        Application application15 = new Application();
        application15.setApplicationId(UUID.randomUUID().toString());
        application15.setApplicant(applicant3);
        application15.setBriefBio("Excellent communication skills in written and spoken French.");
        application15.setStatus("Application Received");
        application15.setJob(job3);
        application1.setReviewer1(reviewer);
        application1.setReviewer2(reviewer1);

        Application application16 = new Application();
        application16.setApplicationId(UUID.randomUUID().toString());
        application16.setApplicant(applicant4);
        application16.setBriefBio("Computer literate in MS Office applications, Internet and email applications.");
        application16.setStatus("Application Received");
        application16.setJob(job3);
        application1.setReviewer1(reviewer1);
        application1.setReviewer2(Jackson);

        // Insert to db
        mongoTemplate.insert(jobJava, "job");
        mongoTemplate.insert(jobObjectiveC, "job");
        mongoTemplate.insert(jobPython, "job");
        mongoTemplate.insert(job3, "job");
        mongoTemplate.insert(job4, "job");
        mongoTemplate.insert(job5, "job");
        mongoTemplate.insert(job6, "job");
        mongoTemplate.insert(recuriter1, "recuriter");
        mongoTemplate.insert(recuriter2, "recuriter");
        mongoTemplate.insert(applicant1, "applicant");
        mongoTemplate.insert(applicant2, "applicant");
        mongoTemplate.insert(applicant3, "applicant");
        mongoTemplate.insert(applicant4, "applicant");
        mongoTemplate.insert(reviewer, "reviewer");
        mongoTemplate.insert(Jackson, "reviewer");
        mongoTemplate.insert(reviewer1, "reviewer");
        mongoTemplate.insert(application1, "application");
        mongoTemplate.insert(application2, "application");
        mongoTemplate.insert(application3, "application");
        mongoTemplate.insert(application4, "application");
        mongoTemplate.insert(application5, "application");
        mongoTemplate.insert(application6, "application");
        mongoTemplate.insert(application7, "application");
        mongoTemplate.insert(application8, "application");
        mongoTemplate.insert(application9, "application");
        mongoTemplate.insert(application10, "application");
        mongoTemplate.insert(application11, "application");
        mongoTemplate.insert(application12, "application");
        mongoTemplate.insert(application13, "application");
        mongoTemplate.insert(application14, "application");
        mongoTemplate.insert(application15, "application");
        mongoTemplate.insert(application16, "application");

        return Response.status(200).entity("Howdy, dude!").build();
    }
}
