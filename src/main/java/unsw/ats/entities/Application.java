package unsw.ats.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created with IntelliJ IDEA.
 * User: mendy
 * Date: 5/13/12
 * Time: 4:04 PM
 * To change this template use File | Settings | File Templates.
 */
@Document
public class Application {
    @Id
    private String applicationId;

    @DBRef
    private Applicant applicant;

    @DBRef
    private Job job;
    @DBRef
    private Reviewer reviewer1;
    @DBRef
    private Reviewer reviewer2;

    public Reviewer getReviewer1() {
        return reviewer1;
    }

    public void setReviewer1(Reviewer reviewer1) {
        this.reviewer1 = reviewer1;
    }

    public Reviewer getReviewer2() {
        return reviewer2;
    }

    public void setReviewer2(Reviewer reviewer2) {
        this.reviewer2 = reviewer2;
    }

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

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

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
