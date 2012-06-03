package client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.thoughtworks.xstream.XStream;
import unsw.ats.entities.Reviewer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;
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
            session.setAttribute("userId", userId.trim());
        } else {
            userId = (String)session.getAttribute("userId");
        }
        if (userId == null) {
            response.sendRedirect("./");
            return;
        }

        String scope = (String)request.getParameter("scope");
        if (scope == null) {
            response.sendRedirect("./");
        }
        if ("init".equals(scope)) {
        /* Init */
            String userType = (String)request.getParameter("userType");
            session.setAttribute("userType", userType);
            response.sendRedirect("controller?scope=" + userType);
        } else if ("recuriter".equals(scope) || "reviewer".equals(scope) || "applicant".equals(scope)) {
            request.getRequestDispatcher(scope + ".jsp").forward(request, response);
        }
        else if ("apply".equals(scope)) {
            String jobId = request.getParameter("jobId");
            request.setAttribute("jobId", jobId);
            request.getRequestDispatcher("apply.jsp").forward(request, response);
        }
        else if ("createJob".equals(scope)) {
        /* create job */
            Map<String, String[]> parameterMap = request.getParameterMap();
            ClientResponse clientResponse = submitRequest(SERVICE_ADDRESS + "jobs/" + userId + "/create", parameterMap, "post");
            if (clientResponse.getStatus() == 200) {
                request.setAttribute("successMessage", "Job Created successfully");
                String newJobId = clientResponse.getEntity(String.class);
                request.setAttribute("successHtml", "controller?scope=viewJob&id=" + newJobId);
                request.setAttribute("linkName", "View Job Details");
                request.getRequestDispatcher("success.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Job Creation failed");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
            System.out.print(request.getParameterMap());
        } else if ("viewAllJobs".equals(scope)) {
        /* View all jobs */
            Client client = Client.create();
            String queryParam = "?";
            String title = request.getParameter("title");
            String from = request.getParameter("from");
            String to = request.getParameter("to");
            String location = request.getParameter("location");
            String state = request.getParameter("state");


            if (title != null && !title.trim().equals(""))
                queryParam += "title=" + URLEncoder.encode(title.trim()) + "&";
            if (from != null && !from.trim().equals(""))
                queryParam += "from=" + from.trim() + "&";
            if (to != null && !to.trim().equals(""))
                queryParam += "to=" + to.trim() + "&";
            if (location != null && !location.trim().equals(""))
                queryParam += "location=" + URLEncoder.encode(location.trim()) + "&";
            if (state != null && !state.trim().equals(""))
                queryParam += "state=" + state.trim();
            WebResource webResource = client.resource(SERVICE_ADDRESS + "jobs/" + userId + "/all" + queryParam);
            ClientResponse clientResponse = webResource.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
            if (clientResponse.getStatus() == 200) {
                String allJobsXML = clientResponse.getEntity(String.class);
                request.setAttribute("allJobsXML", allJobsXML);
                if (session.getAttribute("userType").equals("recuriter")) {
                    request.getRequestDispatcher("allJobsRecuriter.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("allJobs.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("errorMessage", "Get all jobs Failed: " + clientResponse.toString());
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
            client.destroy();
        } else if ("viewJob".equals(scope)) {
            /* View a job  */
            String jobId = request.getParameter("id");
            Client client = Client.create();
            WebResource webResource = client.resource(SERVICE_ADDRESS + "jobs/" + userId + "/detail?id=" + jobId);
            ClientResponse clientResponse = webResource.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
            if (clientResponse.getStatus() == 200) {
                String allJobsXML = clientResponse.getEntity(String.class);
                request.setAttribute("allJobsXML", allJobsXML);
                request.getRequestDispatcher("job.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Get all jobs Failed: " + clientResponse.toString());
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
            client.destroy();
        }else if ("createApplication".equals(scope)) {
        /* Create application */
            Map<String, String[]> parameterMap = request.getParameterMap();
            ClientResponse clientResponse = submitRequest(SERVICE_ADDRESS + "applications/" + userId + "/create", parameterMap, "post");
            if (clientResponse.getStatus() == 200) {
                String applicationId = clientResponse.getEntity(String.class);
                request.setAttribute("successMessage", "Application Created successfully");
                request.setAttribute("successHtml", "controller?scope=applicationDetail&id=" + applicationId);
                request.setAttribute("linkName", "view application detail");
                request.getRequestDispatcher("success.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Application Creation failed: " + clientResponse.getEntity(String.class));
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } else if ("myApplications".equals(scope)) {
        /* my applications */
            String jobId = request.getParameter("jobId");
            String myApplicationsXML = getMyApplications(userId, jobId, request, response);
            if (myApplicationsXML.length() > 0) {
                request.setAttribute("myApplicationsXML", myApplicationsXML);
                request.getRequestDispatcher("myApplications.jsp").forward(request, response);
            } else {
                return;
            }
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
                request.setAttribute("errorMessage", "Get application Failed: " + clientResponse.getEntity(String.class));
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        }else if("withdraw".equals(scope)){
            String applicationId = request.getParameter("id");
            Client client = Client.create();
            WebResource webResource = client.resource(SERVICE_ADDRESS + "applications/" + userId + "/delete?id=" + applicationId);
            ClientResponse clientResponse = webResource.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
            if (clientResponse.getStatus() == 200) {
                response.sendRedirect("controller?scope=myApplications");
                return;
            } else {
                request.setAttribute("errorMessage", "Delete application Failed: " + clientResponse.getEntity(String.class));
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        }
        else if ("updateApplication".equals(scope)) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            ClientResponse clientResponse = submitRequest(SERVICE_ADDRESS + "applications/" + userId + "/update", parameterMap, "put");
            if (clientResponse.getStatus() == 200) {
                String applicationId = clientResponse.getEntity(String.class);
                request.setAttribute("successMessage", "Application Updated successfully");
                request.setAttribute("successHtml", "controller?scope=applicationDetail&id=" + applicationId);
                request.setAttribute("linkName", "view application detail");
                request.getRequestDispatcher("success.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Application update failed: " + clientResponse.getEntity(String.class));
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } else if ("applicationsToMyJobs".equals(scope)) {
            String jobId = request.getParameter("jobId");
            String myApplicationsXML = getMyApplications(userId, jobId, request, response);
            if (myApplicationsXML.length() > 0) {
                request.setAttribute("myApplicationsXML", myApplicationsXML);
                request.getRequestDispatcher("ApplicationsToMyJobs.jsp").forward(request, response);
            } else {
                return;
            }
        }
        else if ("assignToReviewers".equals(scope)) {
            Client client = Client.create();
            WebResource webResource = client.resource(SERVICE_ADDRESS + "user/" + userId + "/reviewers");
            ClientResponse clientResponse = webResource.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
            if (clientResponse.getStatus() == 200) {
                String reviewersXML = clientResponse.getEntity(String.class);
                XStream xStream = new XStream();
                String applicationId = request.getParameter("id");
                List<Reviewer> reviewers = (List<Reviewer>)xStream.fromXML(reviewersXML);
                request.setAttribute("reviewers", reviewers);
                request.setAttribute("id", applicationId);
                request.getRequestDispatcher("assignToReviewers.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Get my reviewers Failed: " + clientResponse.getEntity(String.class));
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } else if ("applicationsToBeReviewed".equals(scope)) {
            String jobId = request.getParameter("jobId");
            String myApplicationsXML = getMyApplications(userId, jobId, request, response);
            if (myApplicationsXML.length() > 0) {
                request.setAttribute("myApplicationsXML", myApplicationsXML);
                request.getRequestDispatcher("applicationsToReview.jsp").forward(request, response);
            } else {
                return;
            }
        } else if ("reviewApplication".equals(scope)) {
            String applicationId = request.getParameter("id");
            if (applicationId == null || applicationId.trim().equals("")) {
                request.setAttribute("errorMessage", "Application update failed: ");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
            Client client = Client.create();
            WebResource webResource = client.resource(SERVICE_ADDRESS + "applications/" + userId + "/detail?id=" + applicationId);
            ClientResponse clientResponse = webResource.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
            if (clientResponse.getStatus() == 200) {
                String applicationXML = clientResponse.getEntity(String.class);
                request.setAttribute("applicationXML", applicationXML);
                request.getRequestDispatcher("reviewApplication.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Get application Failed: " + clientResponse.getEntity(String.class));
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
            client.destroy();
        } else if("doAssign".equals(scope)){
            Map<String, String[]> parameterMap = request.getParameterMap();
//            Client client = Client.create();
//            WebResource webResource = client.resource(SERVICE_ADDRESS + "applications/" +userId + "/assign");
            ClientResponse clientResponse = submitRequest(SERVICE_ADDRESS + "applications/" + userId + "/assign", parameterMap, "post");
//            ClientResponse clientResponse = webResource.accept(MediaType.APPLICATION_XML).post(ClientResponse.class);
            if (clientResponse.getStatus() == 200) {
                request.setAttribute("successMessage", "Application assigned successfully");
                request.setAttribute("successHtml", "controller?scope=applicationsToMyJobs");
                request.setAttribute("linkName", "back to applications list");
                request.getRequestDispatcher("success.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Application Creation failed: " + clientResponse.getEntity(String.class));
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }

        } else if ("doReview".equals(scope)) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            ClientResponse clientResponse = submitRequest(SERVICE_ADDRESS + "applications/" + userId + "/review", parameterMap, "put");
            if (clientResponse.getStatus() == 200) {
                request.setAttribute("successMessage", "Application reviewed successfully");
                request.setAttribute("successHtml", "controller?scope=applicationsToBeReviewed");
                request.setAttribute("linkName", "Back to review list");
                request.getRequestDispatcher("success.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Application review failed: " + clientResponse.getEntity(String.class));
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } else if("applicationDetail".equals(scope)){
            String applicationId = request.getParameter("id");
            if (applicationId == null || applicationId.trim().equals("")) {
                request.setAttribute("errorMessage", "Application update failed: ");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
            Client client = Client.create();
            WebResource webResource = client.resource(SERVICE_ADDRESS + "applications/" + userId + "/detail?id=" + applicationId);
            ClientResponse clientResponse = webResource.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
            if (clientResponse.getStatus() == 200) {
                String applicationXML = clientResponse.getEntity(String.class);
                request.setAttribute("applicationXML", applicationXML);
                request.getRequestDispatcher("application.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Get application Failed: " + clientResponse.getEntity(String.class));
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
            client.destroy();

        }
        else if("final".equals(scope)){
            String applicationId = request.getParameter("id");
            if (applicationId == null || applicationId.trim().equals("")) {
                request.setAttribute("errorMessage", "Application update failed: ");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
            Client client = Client.create();
            WebResource webResource = client.resource(SERVICE_ADDRESS + "applications/" + userId + "/detail?id=" + applicationId);
            ClientResponse clientResponse = webResource.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
            if (clientResponse.getStatus() == 200) {
                String applicationXML = clientResponse.getEntity(String.class);
                request.setAttribute("recuriterId", userId);
                request.setAttribute("applicationXML", applicationXML);
                request.getRequestDispatcher("finalDecision.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Get application Failed: " + clientResponse.getEntity(String.class));
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
            client.destroy();
        }else if("finalDecision".equals(scope)){
            Map<String, String[]> parameterMap = request.getParameterMap();
            ClientResponse clientResponse = submitRequest(SERVICE_ADDRESS + "applications/" + userId + "/finalDecision", parameterMap, "put");
            if (clientResponse.getStatus() == 200) {
                request.setAttribute("successMessage", "Application reviewed successfully");
                request.setAttribute("successHtml", "controller?scope=applicationsToMyJobs");
                request.setAttribute("linkName", "back to applications list");
                request.getRequestDispatcher("success.jsp").forward(request, response);
//                response.sendRedirect("controller?scope=applicationsToMyJobs");
                return;
            } else {
                request.setAttribute("errorMessage", "Application review failed: " + clientResponse.getEntity(String.class));
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


    private String getMyApplications(
            String userId,
            String jobId,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        String returnXML = "";
        Client client = Client.create();
        WebResource webResource = client.resource(SERVICE_ADDRESS + "applications/" + userId + "/myApplications?jobId=" + jobId);
        ClientResponse clientResponse = webResource.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
        if (clientResponse.getStatus() == 200) {
            returnXML = clientResponse.getEntity(String.class);

        } else {
            request.setAttribute("errorMessage", "Get my applications Failed: " + clientResponse.getEntity(String.class));
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        client.destroy();
        return returnXML;
    }

    private ClientResponse submitRequest(String clientResource, Map<String, String[]> parameterMap, String method) {
        Client client = Client.create();
        MultivaluedMap formData = new MultivaluedMapImpl();
        for (Object key : parameterMap.keySet()) {
            String keyString = (String)key;
            String valueString = parameterMap.get(key)[0].toString();
            if(parameterMap.get(key).length == 2){
                 valueString += "," + parameterMap.get(key)[1].toString();
            }
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
