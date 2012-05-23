package unsw.ats.RestController;

import com.sun.org.apache.xml.internal.dtm.DTMIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.sun.org.apache.xml.internal.dtm.ref.DTMNodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import unsw.ats.MongoService.ApplicantService;
import unsw.ats.MongoService.JobService;
import unsw.ats.MongoService.RecuriterService;
import unsw.ats.MongoService.ReviewerService;
import unsw.ats.adapter.XmlAdapter;
import unsw.ats.entities.Applicant;
import unsw.ats.entities.Job;
import unsw.ats.entities.Recuriter;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import  org.w3c.dom.*;
import unsw.ats.entities.Reviewer;

import javax.xml.parsers.*;

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
 *
 * 4917b6a7-20da-47a2-b8f4-181a771b51a1
 */
@Component
@Path("/jobs/{userId: [^/]*}") /*make userId can be empty*/
public class JobsController {

    @Autowired
    private JobService service;

    @Autowired
    private RecuriterService recuriterService;

    @Autowired
    private ReviewerService reviewerService;

    @Autowired
    private ApplicantService applicantService;

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_XML)
    public Response create(
            @PathParam("userId") String userId,
            @QueryParam(value = "title") String title,
            @QueryParam(value = "jobDesc") String jobDesc,
            @QueryParam(value = "salary") float salary,
            @QueryParam(value = "location") String location,
            @QueryParam(value = "closingDate")String closingDate
    ) throws ParseException {
        if (!validate(userId, 1)) {
            return Response.status(401).entity("Unauthorized").build();
        }
        Job job = new Job();
        job.setJobId(UUID.randomUUID().toString());
        job.setJobTitle(title);
        job.setJobDesc(jobDesc);
        job.setSalary(salary);
        DateFormat formatter ;
        Date date ;
        formatter = new SimpleDateFormat("dd-MMM-yy");
        date = (Date)formatter.parse(closingDate);
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        job.setClosingDate(cal);
        job.setLocation(location);                http://www.youtube.com/watch?v=ir-tZS3TaxM&ob=av3e
        job.setStatus(true);
        Recuriter recuriter = findRecuriter(userId);
        job.setRecuriter(recuriter);
        job  = service.create(job);

        return Response.status(200)
                .entity("<create><status>success</status><jobId>" + job.getJobId() + "</jobId></create>")
                .build();
    }

    /**
     * http://localhost:8080/ApplicantTrackingSystem/rest/jobs//detail?id=foobar
     * @param id
     * @return
     */
    @GET
    @Path("/detail")
    @Produces(MediaType.APPLICATION_XML)
    public Response detail(
            @PathParam("userId") String userId,
            @QueryParam(value = "id") String id
    ) throws XPathExpressionException {
        if (!validate(userId, 7)) {
            return Response.status(401).entity("Unauthorized").build();
        }
        String jobsXml = XmlAdapter.getJobXML(service.findById(id));
        //TODO: get the job, use XQuery!
//        XPathFactory factory = XPathFactory.newInstance();
//        XPath xPath = factory.newXPath();
//        XPathExpression expression = xPath.compile("//unsw.ats.entities.Job[jobId='" + id + "']//text()");
//        InputSource inputSource = new InputSource(new StringReader(jobsXml));
//        Object result = expression.evaluate(inputSource, XPathConstants.NODESET);
//        DTMNodeList nodes = (DTMNodeList) result;
//        for (int i = 0; i < nodes.getLength(); i ++) {
////            if(!nodes.item(i).getNodeValue().trim().equals("")) {
//
//                System.out.println(nodes.item(i).getNodeValue());
////             + " @ " +
////            nodes.item(i).getParentNode().getNodeName()
////            }
//        }

        //http://www.ibm.com/developerworks/xml/library/x-xjavaxquery/
        return Response.status(200)
                .entity(jobsXml)
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
    ) throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {
        if (!validate(userId, 7)) {
            return Response.status(401).entity("Unauthorized").build();
        }
        String jobsXml = XmlAdapter.getJobsXML(service.readAll());
//        XPathFactory factory = XPathFactory.newInstance();
//        XPath xPath = factory.newXPath();
//        XPathExpression expression = xPath.compile("//unsw.ats.entities.Job/jobId/text()");
//        InputSource inputSource = new InputSource(new StringReader(jobsXml));
//        Object result = expression.evaluate(inputSource, XPathConstants.NODESET);
//
//        DTMNodeList nodes = (DTMNodeList) result;
//        for (int i = 0; i < nodes.getLength(); i ++) {
//             System.out.println(nodes.item(i).getNodeValue());
//        }
        //TODO: search for the jobs
        //http://www.ibm.com/developerworks/xml/library/x-xjavaxquery/
        return Response.status(200)
                .entity(jobsXml)
                .build();

    }

    private Recuriter findRecuriter(String userId){
        for(Recuriter r: recuriterService.readAll()){
            if(r.getUserId().equals(userId))
                return r;
        }
        return null;
    }

    /**
     * validate user from applicant, reviewer or recuriter
     * @param userId
     * @param type      001
     * @return
     */
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
                if(r.getReviewerId().equals(userId))
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