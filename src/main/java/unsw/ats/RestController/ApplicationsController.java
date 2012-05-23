package unsw.ats.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import unsw.ats.MongoService.ApplicationService;
import unsw.ats.adapter.XmlAdapter;
import unsw.ats.entities.Application;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: yousilin
 * Date: 13/05/12
 * Time: 5:05 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/applications/{userId: [^/]*}") /*make userId can be empty*/
public class ApplicationsController {
    @Autowired
    private ApplicationService applicationService;
    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_XML)
    public Response create(
            @PathParam("userId") String userId,
            @QueryParam(value = "jobId") String jobId,
            @QueryParam(value = "briefBio") String briefBio,
            @QueryParam(value = "salary") float salary
//            @QueryParam(value = "status") String status
            //...
    ) {
        if (!validate(userId)) {
            return Response.status(401).entity("Unauthorized").build();
        }
        Application application = new Application();
        application.setApplicationId(userId);
        application.setApplicantId(UUID.randomUUID().toString());
        application.setJobId(jobId);
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
        String jobsXml = XmlAdapter.getJobXML(applicationService.findById(id));
        return Response.status(200)
                .entity(jobsXml)
                .build();
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
