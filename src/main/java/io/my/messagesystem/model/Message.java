package io.my.messagesystem.model;

import javax.persistence.*;

@Entity // Tells Hibernate to make a table out of this class
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "author")
    private String author;

    @Column(name = "content")
    private String content;

    public Message() {
    }

    public Message(String title, String createTime, String author, String content) {
        this.title = title;
        this.createTime = createTime;
        this.author = author;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
