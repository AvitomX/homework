package ru.tfs.jdbc.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Message {
    private Long id;
    private String text;
    private User author;
    private List<Comment> comments = new ArrayList<>();

    public Message() {
    }

    public Message(String text, User author) {
        this.text = text;
        this.author = author;
    }

    public Message(Long id, String text, User author) {
        this.id = id;
        this.text = text;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public List<Comment> getComments() {
        return comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return getId().equals(message.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
