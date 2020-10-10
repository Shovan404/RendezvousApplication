package com.fod.foodorderdelivery.Model;

public class Feedback {

    private String email;
    private String feedbackmsg;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFeedbackmsg() {
        return feedbackmsg;
    }

    public void setFeedbackmsg(String feedbackmsg) {
        this.feedbackmsg = feedbackmsg;
    }

    public Feedback(String email, String feedbackmsg) {
        this.email = email;
        this.feedbackmsg = feedbackmsg;
    }
}
