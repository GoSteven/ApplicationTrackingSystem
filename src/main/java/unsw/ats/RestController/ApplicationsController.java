package unsw.ats.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unsw.ats.MongoService.ApplicantService;
import unsw.ats.MongoService.ApplicationService;
import unsw.ats.MongoService.JobService;
import unsw.ats.adapter.XmlAdapter;
import unsw.ats.entities.Applicant;
import unsw.ats.entities.Application;
import unsw.ats.entities.Job;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: yousilin
 * Date: 13/05/12
 * Time: 5:05 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
@Path("/applications/{userId: [^/]*}") /*make userId can be empty*/
public class ApplicationsController {
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private JobService jobService;
    @Autowired
    private ApplicantService applicantService;
    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_XML)
    public Response create(
            @PathParam("userId") String userId,
            @FormParam(value = "jobId") String jobId,
            @FormParam(value = "briefBio") String briefBio,
            @FormParam(value = "salary") float salary
    ) {
        /**
         * Regarding HTTP error CODE , refer to : http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.10
         */
        if (!validate(userId)) {
            return Response.status(401).entity("Unauthorized").build();
        }
        Application application = new Application();
        Applicant applicant = applicantService.findById(userId);
        if (applicant == null) {
            return Response.status(412).entity("No such applicant").build();
        }
        application.setApplicant(applicant);
        application.setApplicationId(userId);
        Job job = jobService.findById(jobId);
        if (applicant == null) {
            return Response.status(412).entity("No such job").build();
        }
        /* cannot apply to the same job twice */
        for (Application a : applicationService.readAll()) {
            if (a.getJob().getJobId().equals(jobId) && a.getApplicant().getApplicantId().equals(userId)) {
                return Response.status(412).entity("Cannot apply to the same job twice").build();
            }
        }
        application.setJob(job);
        application.setBriefBio(briefBio);
        application.setSalary(salary);
        application = applicationService.create(application);
        return Response.status(200)
                .entity("<create><status>success</status><applicationId>" + application.getApplicationId() + "</applicationId></create>")
                .build();
    }

    /**
     * http://localhost:8080/ApplicantTrackingSystem/rest/applications//detail?id=foobar
     * @param id
     * @return
     */
    @GET
    @Path("/detail")
    @Produces(MediaType.APPLICATION_XML)
    public Response detail(
            @PathParam("userId") String userId,
            @QueryParam(value = "id") String id
    ) {
        if (!validate(userId)) {
            return Response.status(401).entity("Unauthorized").build();
        }
        //TODO: get the application, encode in xml
        String applicationXML = XmlAdapter.getApplicationXML(applicationService.findById(id));
        return Response.status(200)
                .entity(applicationXML).build();
    }

    @GET
    @Path("/myApplications")
    @Produces(MediaType.APPLICATION_XML)
    public Response myApplications(
            @PathParam("userId") String userId
    ) {
        if (!validate(userId)) {
            return Response.status(401).entity("Unauthorized").build();
        }
        List<Application> applications = applicationService.readAll();
        List<Application> myApplications = new ArrayList<Application>();
        for (Application application : applications) {
            if (userId.endsWith(application.getApplicant().getApplicantId())) {
                myApplications.add(application);
            }
        }
        String myApplicationsXML = XmlAdapter.getApplicationsXML(myApplications);
        return Response.status(200)
                .entity(myApplicationsXML).build();
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_XML)
    public Response update(
            @PathParam("userId") String userId,
            @QueryParam("id") String id,
            @QueryParam("title") String title,
            @QueryParam(value = "content") String content
            //...
    ) {
        if (!validate(userId)) {
            return Response.status(401).entity("Unauthorized").build();
        }
        //TODO: update by applicant or reviewer
        return Response.status(200)
                .entity("TODO: return the latest representation of the application. id: " )
                .build();
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_XML)
    public Response delete(
            @PathParam("userId") String userId,
            @QueryParam("id") String id
    ) {
        if (!validate(userId)) {
            return Response.status(401).entity("Unauthorized").build();
        }
        //TODO: delete
        return Response.status(200)
                .entity("TODO: return if successful")
                .build();
    }

    private boolean validate(String userId) {
        return true;
    }

}
