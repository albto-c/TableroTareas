package com.albtoc.TableroTareas.infraestructure.repository;

import com.albtoc.TableroTareas.infraestructure.entity.TaskStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskStatusRepositoryJpa extends JpaRepository<TaskStatusEntity, Long> {
}
