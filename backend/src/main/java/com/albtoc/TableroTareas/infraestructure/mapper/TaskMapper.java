package com.albtoc.TableroTareas.infraestructure.mapper;

import com.albtoc.TableroTareas.domain.models.Task;
import com.albtoc.TableroTareas.infraestructure.entity.TaskEntity;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class TaskMapper {
    public Task entityToModel(TaskEntity taskEntity) {
        if (taskEntity == null) {
            throw new NullPointerException();
        }
        Task task = new Task();
        task.setId(taskEntity.getId());
        task.setTitle(taskEntity.getTitle());
        task.setDescription(taskEntity.getDescription());
        task.setDate(taskEntity.getDate());
        task.setTaskStatus(taskEntity.getTaskStatus().getId());
        task.setUser(taskEntity.getUser().getId());
        return task;
    }
}
