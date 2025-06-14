package com.albtoc.TableroTareas.application.ports.in;

import com.albtoc.TableroTareas.domain.dtos.TaskDto;
import com.albtoc.TableroTareas.domain.exceptions.UserNotFoundException;
import com.albtoc.TableroTareas.domain.models.Task;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskService {
    Optional<List<Task>> getTasksByIdUserAndMonthAndYear(Long idUser, String month, String year);

    Optional<List<Task>> getTasksByIdUserAndDate(Long idUser, LocalDate date);

    Task createTask(TaskDto taskDto, String username) throws UserNotFoundException;

    void deleteTask(Long id);

    Task updateTaskStatus(Long idTask, Long idTaskStatus);
}
