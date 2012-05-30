package unsw.ats.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unsw.ats.MongoService.ReviewerService;
import unsw.ats.adapter.XmlAdapter;
import unsw.ats.entities.Reviewer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yousilin
 * Date: 30/05/12
 * Time: 1:24 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
@Path("/user/{userId: [^/]*}") /*make userId can be empty*/
public class UserController extends ControllerBase {
    @Autowired
    private ReviewerService reviewerService;

    @GET
    @Path("/reviewers")
    @Produces(MediaType.APPLICATION_XML)
    public Response getReviewers(
            @PathParam("userId") String userId
    ) {
        if (!validate(userId, 1)) {
            return javax.ws.rs.core.Response.status(401).entity("Unauthorized").build();
        }
        List<Reviewer> reviewerList = reviewerService.readAll();
        String reviewerListXML = XmlAdapter.getReviewersXML(reviewerList);
        return Response.status(200)
                .entity(reviewerListXML).build();
    }


}
