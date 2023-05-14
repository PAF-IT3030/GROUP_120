package com.sept.rest.webservices.restfulwebservices.post;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "Todo")
public class Post {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String description;
    private Date targetDate;
    private boolean isDone;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<PostComment> comments = new ArrayList<>();

    public Post() {

    }

    public Post(long id, String username, String description, Date targetDate, boolean isDone) {
        super();
        this.id = id;
        this.username = username;
        this.description = description;
        this.targetDate = targetDate;
        this.isDone = isDone;
        this.comments = new ArrayList<PostComment>();
    }
    
    public Post addComment(PostComment comment) {
        // Check if a comment with the same ID already exists in the list
        for (int i = 0; i < comments.size(); i++) {
            if (comments.get(i).getId() == comment.getId()) {
                // Update the existing comment with the new values
                comments.set(i, comment);
                return this;
            }
        }
        // If a comment with the same ID does not exist in the list, add the new comment
        this.comments.add(comment);
        return this;
    }

    public List<PostComment> getComments() {
        return this.comments;
    }

    public void setComments(List<PostComment> list) {
        this.comments = list;
    }
    
    public Post addComments(List<PostComment> comments) {
        // Loop through the new comments list and add each one to the existing comments list
        for (PostComment comment : comments) {
            // Check if a comment with the same ID already exists in the list
            boolean commentExists = false;
            for (int i = 0; i < this.comments.size(); i++) {
                if (this.comments.get(i).getId() == comment.getId()) {
                    // Update the existing comment with the new values
                    this.comments.set(i, comment);
                    commentExists = true;
                    break;
                }
            }
            // If a comment with the same ID does not exist in the list, add the new comment
            if (!commentExists) {
                this.comments.add(comment);
            }
        }
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    public void printComments() {
        System.out.println("Print comments");
        System.out.println(Arrays.toString(comments.toArray()));
        System.out.println("Print first comment");
        System.out.println(comments.get(0).toString());
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Post other = (Post) obj;
        if (id != other.id)
            return false;
        return true;
    }
}
