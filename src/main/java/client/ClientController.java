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
import javax.ws.rs.core.MediaType;
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
        if (scope == null) {
            response.sendRedirect("./");
        }
        if ("init".equals(scope)) {
        /* Init */
            String userType = (String)request.getParameter("userType");
            response.sendRedirect(userType + ".jsp");
        } else if ("createJob".equals(scope)) {
        /* create job */
            Map<String, String[]> parameterMap = request.getParameterMap();
            ClientResponse clientResponse = submitRequest(SERVICE_ADDRESS + "jobs/" + userId + "/create", parameterMap, "post");
            if (clientResponse.getStatus() == 200) {
                request.setAttribute("successMessage", "Job Created successfully");
                //TODO: linkToNewJob
                request.setAttribute("successHtml", "linkToNewJob");
                request.getRequestDispatcher("success.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Job Creation failed");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
            System.out.print(request.getParameterMap());
        } else if ("viewAllJobs".equals(scope)) {
        /* View all jobs */
            Client client = Client.create();
            WebResource webResource = client.resource(SERVICE_ADDRESS + "jobs/" + userId + "/all");
            ClientResponse clientResponse = webResource.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
            if (clientResponse.getStatus() == 200) {
                String allJobsXML = clientResponse.getEntity(String.class);
                request.setAttribute("allJobsXML", allJobsXML);
                request.getRequestDispatcher("allJobs.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Get all jobs Failed: " + clientResponse.toString());
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
            client.destroy();
        } else if ("createApplication".equals(scope)) {
        /* Create application */
            Map<String, String[]> parameterMap = request.getParameterMap();
            ClientResponse clientResponse = submitRequest(SERVICE_ADDRESS + "applications/" + userId + "/create", parameterMap, "post");
            if (clientResponse.getStatus() == 200) {
                request.setAttribute("successMessage", "Application Created successfully");
                //TODO: linkToNewApplication
                request.setAttribute("successHtml", "linkToNewApplication");
                request.getRequestDispatcher("success.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Application Creation failed: " + clientResponse.getEntity(String.class));
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } else if ("myApplications".equals(scope)) {
        /* my applications */
            Client client = Client.create();
            WebResource webResource = client.resource(SERVICE_ADDRESS + "applications/" + userId + "/myApplications");
            ClientResponse clientResponse = webResource.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
            if (clientResponse.getStatus() == 200) {
                String myApplicationsXML = clientResponse.getEntity(String.class);
                request.setAttribute("myApplicationsXML", myApplicationsXML);
                request.getRequestDispatcher("myApplications.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Get my applications Failed: " + clientResponse.getEntity(String.class));
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
            client.destroy();
        } else if ("editApplication".equals(scope)) {
        /* edit application */
            // http://localhost:8080/ApplicantTrackingSystem/client/controller?scope=editApplication&id=cd2edfde-9c2e-4dbc-8fa2-208eb9c707e0
            String applicationId = request.getParameter("id");
            Client client = Client.create();
            WebResource webResource = client.resource(SERVICE_ADDRESS + "applications/" + userId + "/detail?id=" + applicationId);
            ClientResponse clientResponse = webResource.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
            if (clientResponse.getStatus() == 200) {
                String applicationXML = clientResponse.getEntity(String.class);
                request.setAttribute("applicationXML", applicationXML);
                request.getRequestDispatcher("editApplication.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Get applications Failed: " + clientResponse.getEntity(String.class));
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } else if ("updateApplication".endsWith(scope)) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            ClientResponse clientResponse = submitRequest(SERVICE_ADDRESS + "applications/" + userId + "/update", parameterMap, "put");
            if (clientResponse.getStatus() == 200) {
                request.setAttribute("successMessage", "Application Updated successfully");
                //TODO: linkToNewApplication
                request.setAttribute("successHtml", "linkToNewApplication");
                request.getRequestDispatcher("success.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Application update failed: " + clientResponse.getEntity(String.class));
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        }


        else {



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
    }

    private ClientResponse submitRequest(String clientResource, Map<String, String[]> parameterMap, String method) {
        Client client = Client.create();
        MultivaluedMap formData = new MultivaluedMapImpl();
        for (Object key : parameterMap.keySet()) {
            String keyString = (String)key;
            String valueString = parameterMap.get(key)[0].toString();
            formData.add(keyString, valueString);
        }
        WebResource webResource = client.resource(clientResource);
        ClientResponse clientResponse = null;
        if ("post".equalsIgnoreCase(method)) {
            clientResponse = webResource.accept(MediaType.APPLICATION_XML).post(ClientResponse.class, formData);
        } else if ("put".equalsIgnoreCase(method)) {
            clientResponse = webResource.accept(MediaType.APPLICATION_XML).put(ClientResponse.class, formData);
        }
        client.destroy();
        return clientResponse;
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request,response);
    }
}
