package com.albtoc.TableroTareas.domain.models;


import java.util.List;

public class TaskResponse {
    private List<Task> tasksList;

    public TaskResponse(List<Task> tasksList) {
        this.tasksList = tasksList;
    }

    public List<Task> getTasksList() {
        return tasksList;
    }

    public void setTasksList(List<Task> tasksList) {
        this.tasksList = tasksList;
    }
}
