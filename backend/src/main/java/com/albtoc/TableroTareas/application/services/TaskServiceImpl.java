package com.albtoc.TableroTareas.application.services;

import com.albtoc.TableroTareas.application.ports.in.TaskService;
import com.albtoc.TableroTareas.application.ports.out.TaskRepositoryService;
import com.albtoc.TableroTareas.application.ports.out.UserRepositoryService;
import com.albtoc.TableroTareas.domain.dtos.TaskDto;
import com.albtoc.TableroTareas.domain.exceptions.UserNotFoundException;
import com.albtoc.TableroTareas.domain.models.Task;
import com.albtoc.TableroTareas.domain.models.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TaskServiceImpl implements TaskService {
    private final TaskRepositoryService taskRepositoryService;
    private final UserRepositoryService userRepositoryService;

    public TaskServiceImpl(TaskRepositoryService taskRepositoryService, UserRepositoryService userRepositoryService) {
        this.taskRepositoryService = taskRepositoryService;
        this.userRepositoryService = userRepositoryService;
    }

    @Override
    public Optional<List<Task>> getTasksByIdUserAndMonthAndYear(Long idUser, String month, String year) {
        return taskRepositoryService.getTasksByIdUserAndMonthAndYear(idUser, month, year);
    }

    @Override
    public Optional<List<Task>> getTasksByIdUserAndDate(Long idUser, LocalDate date) {
        return taskRepositoryService.getTasksByIdUserAndDate(idUser, date);
    }

    @Override
    public Task createTask(TaskDto taskDto, String username) throws UserNotFoundException {
        Optional<User> user = userRepositoryService.findByUsername(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        return taskRepositoryService.createTask(taskDto, username);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepositoryService.deleteTask(id);
    }

    @Override
    public Task updateTaskStatus(Long idTask, Long idTaskStatus) {
        return taskRepositoryService.updateTaskStatus(idTask, idTaskStatus);
    }
}
