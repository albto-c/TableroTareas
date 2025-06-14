package com.albtoc.TableroTareas.application.ports.out;

import com.albtoc.TableroTareas.domain.dtos.TaskDto;
import com.albtoc.TableroTareas.domain.models.Task;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepositoryService {
    Optional<List<Task>> getTasksByIdUserAndMonthAndYear(Long idUser, String month, String year);

    Optional<List<Task>> getTasksByIdUserAndDate(Long idUser, LocalDate date);

    Task createTask(TaskDto taskDto, String username);

    void deleteTask(Long id);

    Task updateTaskStatus(Long id, Long idTaskStatus);
}
