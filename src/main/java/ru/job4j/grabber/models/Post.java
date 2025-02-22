package ru.job4j.grabber.models;

import java.util.Objects;

public class Post {
    private long id;
    private String title;
    private String link;
    private String description;
    private Long time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return String.format("id: %s, title: %s, link: %s, description: %s, time: %s", id, title, link, description, time);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return Objects.equals(title, post.title) && Objects.equals(link, post.link) && Objects.equals(description, post.description)
                && Objects.equals(time, post.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, link, description, time);
    }
}
