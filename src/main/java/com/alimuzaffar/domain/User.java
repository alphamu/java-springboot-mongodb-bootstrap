package com.alimuzaffar.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    @Indexed(unique = false)
    private String document_id;

    @Indexed(unique = true)
    private String user;

    private String workstation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocument_id() {
        return document_id;
    }

    public void setDocument_id(String document_id) {
        this.document_id = document_id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getWorkstation() {
        return workstation;
    }

    public void setWorkstation(String workstation) {
        this.workstation = workstation;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", document_id='" + document_id + '\'' +
                ", user='" + user + '\'' +
                ", workstation='" + workstation + '\'' +
                '}';
    }
}