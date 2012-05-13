package unsw.ats.RestController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * User: yousilin
 * Date: 13/05/12
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/")
public class Controller {
    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response test() {
        return Response.status(200).entity("Howdy, dude!").build();
    }
}
