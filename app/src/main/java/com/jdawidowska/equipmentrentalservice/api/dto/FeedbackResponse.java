package com.jdawidowska.equipmentrentalservice.api.dto;

public class FeedbackResponse {

    private String email;
    private String content;

    public FeedbackResponse() {
    }

    public FeedbackResponse(String email, String content) {
        this.email = email;
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "FeedbackResponseDTO{" +
                "email='" + email + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
