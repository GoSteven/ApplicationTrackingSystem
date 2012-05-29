package unsw.ats.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unsw.ats.MongoService.*;
import unsw.ats.adapter.XmlAdapter;
import unsw.ats.entities.*;

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

    @Autowired
    private RecuriterService recuriterService;

    @Autowired
    private ReviewerService reviewerService;
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
        if (!validate(userId, 4)) {
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
//        TODO
        if (!validate(userId, 5)) {
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
//        TODO:
        if (!validate(userId, 4 + 1)) {
            return Response.status(401).entity("Unauthorized").build();
        }
        List<Application> applications = applicationService.readAll();
        List<Application> myApplications = new ArrayList<Application>();
        for (Application application : applications) {
            if (userId.endsWith(application.getApplicant().getApplicantId())) {
            /* For applicants, display his applications*/
                myApplications.add(application);
            } else if (userId.endsWith(application.getJob().getRecuriter().getUserId())) {
            /* For Recuriters, display the applications, which apply for his job */
                myApplications.add(application);
            }
        }

        String myApplicationsXML = XmlAdapter.getApplicationsXML(myApplications);
        return Response.status(200)
                .entity(myApplicationsXML).build();
    }

    @PUT
    @Path("/assign")
    @Produces(MediaType.APPLICATION_XML)
    public Response assign(
            @PathParam("userId") String userId,
            @PathParam("id") String applicationId,
            @PathParam("reviewerId1") String reviewerId1,
            @PathParam("reviewerId2") String reviewerId2
    ){
        if(!validate(userId, 1)){
            return Response.status(401).entity("Unauthorized").build();
        }
        Application application = applicationService.findById(applicationId);
        if(application == null){
            return Response.status(412).entity("No such application").build();
        }
        if(!application.getJob().getRecuriter().getUserId().equals(userId)){
            return Response.status(401).entity("Unauthorized: you are not authorized to assign this job to the reviewer").build();
        }
        if(!validate(reviewerId1, 2)){
            return Response.status(401).entity("This reviewer is not exist.").build();
        }
        if(!validate(reviewerId2, 2)){
            return Response.status(401).entity("This reviewer is not exist.").build();
        }
        application.setReviewer1(reviewerService.findById(reviewerId1));
        application.setReviewer2(reviewerService.findById(reviewerId2));
        applicationService.update(application);
        return Response.status(200).entity(application.getApplicationId()).build();

    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_XML)
    public Response update(
            @PathParam("userId") String userId,
            @FormParam(value = "id") String applicationId,
            @FormParam(value = "briefBio") String briefBio,
            @FormParam(value = "salary") float salary
    ) {
//        TODO: who can update an application
        if (!validate(userId, 4)) {
            return Response.status(401).entity("Unauthorized").build();
        }
        Application application = applicationService.findById(applicationId);
        if (application == null) {
            return Response.status(412).entity("No such application").build();
        }
        if (!application.getApplicant().getApplicantId().equals(userId)) {
            return Response.status(401).entity("Unauthorized: you are not authorized to update this application").build();
        }
        application.setBriefBio(briefBio);
        application.setSalary(salary);
        applicationService.update(application);
        //TODO: update by applicant or reviewer
        return Response.status(200)
                .entity(application.getApplicationId())
                .build();
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_XML)
    public Response delete(
            @PathParam("userId") String userId,
            @QueryParam("id") String id
    ) {
//        TODO: who can delete an application
        if (!validate(userId, 4)) {
            return Response.status(401).entity("Unauthorized").build();
        }
        //TODO: delete
        return Response.status(200)
                .entity("TODO: return if successful")
                .build();
    }

    private boolean validate(String userId, int type) {
//        return true;
        if ((type & 1) > 0) {
            for (Recuriter r : recuriterService.readAll()) {
                if (r.getUserId().equals(userId))
                    return true;
            }
        }
        if((type & 2) > 0 ){
            for (Reviewer r: reviewerService.readAll()) {
                if(r.getId().equals(userId))
                    return true;
            }
        }
        if((type & 4) > 0){
            for(Applicant a: applicantService.readAll()) {
                if(a.getApplicantId().equals(userId))
                    return true;
            }
        }
        return false;
    }

}
