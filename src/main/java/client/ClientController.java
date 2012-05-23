package client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
* Created with IntelliJ IDEA.
* User: yousilin
* Date: 23/05/12
* Time: 2:25 PM
* To change this template use File | Settings | File Templates.
*/
public class ClientController extends HttpServlet {

    public final static String SERVICE_ADDRESS = "http://localhost:8080/ApplicantTrackingSystem/rest/";
    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        /* set userId in session */
        String userId = (String)request.getParameter("userId");
        if (userId != null) {
            session.setAttribute("userId", userId);
        } else {
            userId = (String)session.getAttribute("userId");
        }
        if (userId == null) {
            throw new ServletException("userId cannot be null");
        }

        String scope = (String)request.getParameter("scope");

        if ("init".equals(scope)) {
            String userType = (String)request.getParameter("userType");
            response.sendRedirect(userType + ".jsp");
        } else if (scope == "createJob") {
            Map parameterMap = request.getParameterMap();
            Client client = Client.create();
            MultivaluedMap formData = new MultivaluedMapImpl();
            for (Object key : parameterMap.keySet()) {
                formData.add((String)key, (String)parameterMap.get(key));
            }
            WebResource webResource = client.resource(SERVICE_ADDRESS + "jobs/" + userId + "/create");
            ClientResponse clientResponse = webResource.accept("application/xml").post(ClientResponse.class, formData);
            System.out.print(request.getParameterMap());
        }



        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>greeting</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>greeting</h1>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request,response);
    }
}
