package com.sept.rest.webservices.restfulwebservices.post;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import java.util.Date;

@Embeddable
public class PostComment {
    @GeneratedValue
    private long id;
    private String username;
    private String description;
    private Date targetDate;

    public PostComment() {
        // Default constructor
    }

    public PostComment(String username, String description, Date targetDate) {
        this.username = username;
        this.description = description;
        this.targetDate = targetDate;
    }
    
    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    @Override
    public String toString() {
        return String.format("%s: %s\n", username, description);
    }
}
