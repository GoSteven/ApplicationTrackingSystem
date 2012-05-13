package unsw.ats.RestController;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * User: yousilin
 * Date: 13/05/12
 * Time: 5:05 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/applications/{userId: [^/]*}") /*make userId can be empty*/
public class ApplicationsController {
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
        //TODO: create new application
        return Response.status(200)
                .entity("TODO: Contain the URI of the new application. userId: " + userId + " title: " + title + " content: " + content)
                .build();
    }

    /**
     * http://localhost:8080/ApplicantTrackingSystem/rest/applications//detail?id=foobar
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
        //TODO: get the applicatino, encode in json
        return Response.status(200)
                .entity("TODO: return the latest representation of the application. id: " + id)
                .build();
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
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
    @Produces(MediaType.APPLICATION_JSON)
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
