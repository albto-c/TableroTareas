package com.albtoc.TableroTareas.domain.models;


import java.time.LocalDate;

public class Task {
    private Long id;
    private String title;
    private String description;
    private LocalDate date;
    private Long taskStatus;
    private Long user;

    public Task(Long id, String title, String description, LocalDate date, Long taskStatus, Long user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.taskStatus = taskStatus;
        this.user = user;
    }

    public Task() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Long taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }
}
