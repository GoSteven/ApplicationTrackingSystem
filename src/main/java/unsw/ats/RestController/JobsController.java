package unsw.ats.RestController;

import com.sun.org.apache.xml.internal.dtm.DTMIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.sun.org.apache.xml.internal.dtm.ref.DTMNodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import unsw.ats.MongoService.ApplicantService;
import unsw.ats.MongoService.JobService;
import unsw.ats.MongoService.RecuriterService;
import unsw.ats.MongoService.ReviewerService;
import unsw.ats.adapter.XmlAdapter;
import unsw.ats.common.Const;
import unsw.ats.entities.Applicant;
import unsw.ats.entities.Job;
import unsw.ats.entities.Recuriter;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.w3c.dom.*;
import unsw.ats.entities.Reviewer;

import javax.xml.parsers.*;

/**
 * Created with IntelliJ IDEA.
 * User: yousilin
 * Date: 13/05/12
 * Time: 3:56 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * http://localhost:8080/ApplicantTrackingSystem/rest/jobs//all
 * or
 * http://localhost:8080/ApplicantTrackingSystem/rest/jobs/userId/all
 * <p/>
 * 4917b6a7-20da-47a2-b8f4-181a771b51a1
 */
@Component
@Path("/jobs/{userId: [^/]*}")
/*make userId can be empty*/
public class JobsController extends ControllerBase {

    @Autowired
    private JobService service;

    @Autowired
    private RecuriterService recuriterService;

    @Autowired
    private ReviewerService reviewerService;

    @Autowired
    private ApplicantService applicantService;

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_XML)
    public Response create(
            @PathParam("userId") String userId,
            @FormParam(value = "title") String title,
            @FormParam(value = "jobDesc") String jobDesc,
            @FormParam(value = "salary") float salary,
            @FormParam(value = "location") String location,
            @FormParam(value = "closingDate") String closingDate
    ) throws ParseException {
        if (!validate(userId, 1)) {
            return Response.status(401).entity("Unauthorized").build();
        }
        Job job = new Job();
        job.setJobId(UUID.randomUUID().toString());
        job.setJobTitle(title);
        job.setJobDesc(jobDesc);
        job.setSalary(salary);
        DateFormat formatter;
        Date date;
        formatter = new SimpleDateFormat("dd-MMM-yyyy");
        date = (Date) formatter.parse(closingDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        job.setClosingDate(cal);
        job.setLocation(location);
        http:
//www.youtube.com/watch?v=ir-tZS3TaxM&ob=av3e
        job.setStatus(true);
        Recuriter recuriter = findRecuriter(userId);
        if (recuriter == null) {
            return Response.status(412).entity("No such recuriter").build();
        }
        job.setRecuriter(recuriter);
        job = service.create(job);

        return Response.status(200)
                .entity(job.getJobId())
                .build();
    }

    /**
     * http://localhost:8080/ApplicantTrackingSystem/rest/jobs//detail?id=foobar
     *
     * @param id
     * @return
     */
    @GET
    @Path("/detail")
    @Produces(MediaType.APPLICATION_XML)
    public Response detail(
            @PathParam("userId") String userId,
            @QueryParam(value = "id") String id
    ) throws XPathExpressionException {
        if (!validate(userId, 7)) {
            return Response.status(401).entity("Unauthorized").build();
        }
        Job job = service.findById(id);
        if (job == null) {
            return Response.status(404).entity("Not Found").build();
        }
        String jobsXml = XmlAdapter.getJobXML(job);

        //http://www.ibm.com/developerworks/xml/library/x-xjavaxquery/
        return Response.status(200)
                .entity(jobsXml)
                .build();
    }

    /**
     * The detail of ALL jobs can be retreived anytime through HTTP GET.
     * The response of HTTP GET must contain the list of selected jobs.
     * Should also support simple search on jobs
     * (e.g., search on job title, or salary range, or jobs that are open/closed)
     *
     * @param title job title
     * @param from  salary range start from
     * @param to    salary range end to
     * @param state open/closed
     * @return
     */
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_XML)
    public Response all(
            @PathParam("userId") String userId,
            @QueryParam(value = "title") String title,
            @QueryParam(value = "from") String from,
            @QueryParam(value = "to") String to,
            @QueryParam(value = "state") String state
    ) throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {
        if (!validate(userId, 7)) {
            return Response.status(401).entity("Unauthorized").build();
        }
        List<Job> allJobs = service.readAll();
        List<Job> shownJob = new ArrayList<Job>();
        for (Job job : allJobs) {
            if (job.getClosingDate().after(Calendar.getInstance())) {
                shownJob.add(job);
            }
        }
        String jobsXml = XmlAdapter.getJobsXML(shownJob);
        //TODO: search for the jobs
        //http://www.ibm.com/developerworks/xml/library/x-xjavaxquery/
        return Response.status(200)
                .entity(jobsXml)
                .build();

    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_XML)
    public Response search(
            @QueryParam("searchKey") String searchKey,
            @QueryParam("searchValue") String searchValue
    ) {

        List<Job> allJobs = service.readAll();
        List<Job> selectedJobs = new ArrayList<Job>();
        if (searchKey.equals(Const.title)) {
            for (Job j : allJobs) {
                if (j.getJobTitle().equals(searchValue)) {
                    selectedJobs.add(j);
                }
            }
        } else if (searchKey.equals(Const.location)) {
            for (Job j : allJobs) {
                if (j.getLocation().equals(searchValue)) {
                    selectedJobs.add(j);
                }

            }
        } else if (searchKey.equals(Const.recuriterName)) {
            for (Job j : allJobs) {
                if (j.getRecuriter().getRecruiterName().equals(searchValue)) {
                    selectedJobs.add(j);
                }
            }
        } else if (searchKey.equals(Const.salary)) {
            float lowBound = Float.valueOf(searchValue.split("-")[0]);
            float upBound = Float.valueOf(searchValue.split("-")[1]);
            for (Job j : allJobs) {
                if (j.getSalary() > lowBound && j.getSalary() < upBound) {
                    selectedJobs.add(j);
                }
            }
        } else if (searchKey.equals(Const.status)) {
            boolean status = searchValue.equals("open");
            for (Job j : allJobs) {
                if (j.isStatus() == status) {
                    selectedJobs.add(j);
                }
            }

        }
        return Response.status(200)
                .entity(selectedJobs)
                .build();

    }

    private Recuriter findRecuriter(String userId) {
        for (Recuriter r : recuriterService.readAll()) {
            if (r.getUserId().equals(userId))
                return r;
        }
        return null;
    }

}