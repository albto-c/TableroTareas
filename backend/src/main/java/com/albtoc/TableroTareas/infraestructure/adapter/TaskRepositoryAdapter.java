package com.albtoc.TableroTareas.infraestructure.adapter;

import com.albtoc.TableroTareas.application.ports.out.TaskRepositoryService;
import com.albtoc.TableroTareas.domain.dtos.TaskDto;
import com.albtoc.TableroTareas.domain.models.Task;
import com.albtoc.TableroTareas.infraestructure.entity.TaskEntity;
import com.albtoc.TableroTareas.infraestructure.entity.TaskStatusEntity;
import com.albtoc.TableroTareas.infraestructure.entity.UserEntity;
import com.albtoc.TableroTareas.infraestructure.mapper.TaskMapper;
import com.albtoc.TableroTareas.infraestructure.repository.TaskRepositoryJpa;
import com.albtoc.TableroTareas.infraestructure.repository.UserRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TaskRepositoryAdapter implements TaskRepositoryService {
    @Autowired
    private TaskRepositoryJpa taskRepositoryJpa;

    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public Optional<List<Task>> getTasksByIdUserAndMonthAndYear(Long idUser, String month, String year) {
        List<TaskEntity> listTasksEntity = taskRepositoryJpa.getTasksByIdUserAndMonthAndYear(idUser, month, year);
        if (listTasksEntity.isEmpty()) {
            return Optional.empty();
        }

        List<Task> listTaskModel = new ArrayList<>();
        listTasksEntity.stream().map(taskMapper::entityToModel).forEach(listTaskModel::add);
        return Optional.of(listTaskModel);
    }

    @Override
    public Optional<List<Task>> getTasksByIdUserAndDate(Long idUser, LocalDate date) {
        List<TaskEntity> listTasksEntity = taskRepositoryJpa.getTasksByIdUserAndDate(idUser, date);
        if (listTasksEntity.isEmpty()) {
            return Optional.empty();
        }

        List<Task> listTaskModel = new ArrayList<>();
        listTasksEntity.stream().map(taskMapper::entityToModel).forEach(listTaskModel::add);
        return Optional.of(listTaskModel);
    }

    @Override
    public Task createTask(TaskDto taskDto, String username) {
        TaskEntity taskEntity = new TaskEntity();
        UserEntity userEntity = userRepositoryJpa.findByUsername(username);
        TaskStatusEntity taskStatusDefault = new TaskStatusEntity();
        taskStatusDefault.setId(1L);

        taskEntity.setTitle(taskDto.getTitle());
        taskEntity.setDescription(taskDto.getDescription());
        taskEntity.setDate(taskDto.getDate());
        taskEntity.setUser(userEntity);
        taskEntity.setTaskStatus(taskStatusDefault);
        return taskMapper.entityToModel(taskRepositoryJpa.save(taskEntity));
    }

    @Override
    public void deleteTask(Long id) {
        taskRepositoryJpa.deleteById(id);
    }

    @Override
    public Task updateTaskStatus(Long id, Long idTaskStatus) {
        TaskEntity taskEntity = taskRepositoryJpa.getReferenceById(id);
        TaskStatusEntity taskStatusEntity = new TaskStatusEntity();
        taskStatusEntity.setId(idTaskStatus);
        taskEntity.setTaskStatus(taskStatusEntity);
        taskRepositoryJpa.save(taskEntity);
        return taskMapper.entityToModel(taskEntity);
    }
}
