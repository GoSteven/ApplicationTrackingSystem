package unsw.ats.entities;

/**
 * Created with IntelliJ IDEA.
 * User: mendy
 * Date: 5/13/12
 * Time: 4:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class Application {
    private String applicationId;
    private String applicantId;
    private String jobId;
    private String briefBio;
    private float salary;
    /**
     * status = 0   application received
     * status = 1   application in review
     * status = 2   decision made by reviewer
     * status = 3   final decision
     */
    private int status;
    /**
     * reviewerIsAccepted = 1  accepted
     * reviewerIsAcceoted = 0  rejected
     */
    private boolean reviewerIsAccepted;
    private String reviewerRecommendations;
    /**
     * finalIsAccepted = 1 accepted
     * finalIsAccepted = 0 rejected
     */
    private boolean finalIsAccepted;

    public String getReviewerRecommendations() {
        return reviewerRecommendations;
    }

    public void setReviewerRecommendations(String reviewerRecommendations) {
        this.reviewerRecommendations = reviewerRecommendations;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isReviewerIsAccepted() {
        return reviewerIsAccepted;
    }

    public void setReviewerIsAccepted(boolean reviewerIsAccepted) {
        this.reviewerIsAccepted = reviewerIsAccepted;
    }

    public boolean isFinalIsAccepted() {
        return finalIsAccepted;
    }

    public void setFinalIsAccepted(boolean finalIsAccepted) {
        this.finalIsAccepted = finalIsAccepted;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getBriefBio() {
        return briefBio;
    }

    public void setBriefBio(String briefBio) {
        this.briefBio = briefBio;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }
}
