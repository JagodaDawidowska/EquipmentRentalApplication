package com.jdawidowska.equipmentrentalservice.model;

public class Feedback {

    private Long id;
    private Long idUser;
    private String content;

    public Feedback() {
    }

    public Feedback(Long id, Long idUser, String content) {
        this.id = id;
        this.idUser = idUser;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", content='" + content + '\'' +
                '}';
    }
}

