package com.alimuzaffar.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "events")
public class Event {

    @Id
    private String id;

    @Indexed(unique = false)
    private String document_id;

    @Indexed(unique = false)
    private String user;

    private String type;

    private String time;

    private String ip;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", document_id='" + document_id + '\'' +
                ", user='" + user + '\'' +
                ", type='" + type + '\'' +
                ", time='" + time + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }
}