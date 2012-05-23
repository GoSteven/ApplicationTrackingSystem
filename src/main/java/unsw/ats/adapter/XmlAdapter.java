package unsw.ats.adapter;

import com.sun.jersey.core.util.StringIgnoreCaseKeyComparator;
import com.thoughtworks.xstream.XStream;
import unsw.ats.entities.Application;
import unsw.ats.entities.Job;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yousilin
 * Date: 15/05/12
 * Time: 9:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class XmlAdapter {
    public static String getJobsXML(List<Job> jobs) {
        XStream xStream = new XStream();
        return xStream.toXML(jobs);
    }

    public static String getJobXML(Job job){
        XStream xStream = new XStream();
        return xStream.toXML(job);
    }

    public static String getJobXML(Application job){
        XStream xStream = new XStream();
        return xStream.toXML(job);
    }

    public static String getApplicationsXML(List<Application> applications) {
        XStream xStream = new XStream();
        return xStream.toXML(applications);
    }
}
