package unsw.ats.entities;

/**
 * Created with IntelliJ IDEA.
 * User: mendy
 * Date: 5/13/12
 * Time: 4:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class applicant {
    private String applicantId;
    private String applicantName;
    private String phoneNumber;

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
