package unsw.ats.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import unsw.ats.MongoService.JobService;
import unsw.ats.MongoService.RecuriterService;
import unsw.ats.adapter.XmlAdapter;
import unsw.ats.entities.Job;
import unsw.ats.entities.Recuriter;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

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
 */
@Component
@Path("/jobs/{userId: [^/]*}") /*make userId can be empty*/
public class JobsController {

    @Autowired
    private JobService service;

    @Autowired
    private RecuriterService recuriterService;

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(
            @PathParam("userId") String userId,
            @QueryParam(value = "title") String title,
            @QueryParam(value = "content") String content
            //...
    ) {
        if (!validate(userId)) {
            return Response.status(401).entity("Unauthorized").build();
        }
        //TODO: set Job details
        Job job = new Job();
        job.setJobTitle("jobTitle");
        job.setJobDesc("jobDesc");
        //TODO: FIND THE RECURITER
        Recuriter recuriter = recuriterService.readAll().get(0);

        job.setRecuriter(recuriter);

        service.create(job);

        return Response.status(200)
                .entity("TODO: Contain the URI of the new job. userId: " + userId + " title: " + title + " content: " + content)
                .build();
    }

    /**
     * http://localhost:8080/ApplicantTrackingSystem/rest/jobs//detail?id=foobar
     * @param id
     * @return
     */
    @GET
    @Path("/detail")
    @Produces(MediaType.APPLICATION_JSON)
    public Response detail(
            @PathParam("userId") String userId,
            @QueryParam(value = "id") String id
    ) {
        if (!validate(userId)) {
            return Response.status(401).entity("Unauthorized").build();
        }
        String jobsXml = XmlAdapter.getJobsXML(service.readAll());
        //TODO: get the job, use XQuery!
        //http://www.ibm.com/developerworks/xml/library/x-xjavaxquery/
        return Response.status(200)
                .entity("TODO: return the latest representation of the job. id: " + id)
                .build();
    }

    /**
     * The detail of ALL jobs can be retreived anytime through HTTP GET.
     * The response of HTTP GET must contain the list of selected jobs.
     * Should also support simple search on jobs
     * (e.g., search on job title, or salary range, or jobs that are open/closed)
     * @param title job title
     * @param from  salary range start from
     * @param to    salary range end to
     * @param state open/closed
     * @return
     */
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_XML)
    public  Response all(
            @PathParam("userId") String userId,
            @QueryParam(value = "title") String title,
            @QueryParam(value = "from") String from,
            @QueryParam(value = "to") String to,
            @QueryParam(value = "state") String state
    ) {
        if (!validate(userId)) {
            return Response.status(401).entity("Unauthorized").build();
        }

        String jobsXml = XmlAdapter.getJobsXML(service.readAll());

        //TODO: search for the jobs
        //http://www.ibm.com/developerworks/xml/library/x-xjavaxquery/
        return Response.status(200)
                .entity(jobsXml)
                .build();

    }

    private boolean validate(String userId) {
        return true;
    }
}