package com.albtoc.TableroTareas.infraestructure.repository;

import com.albtoc.TableroTareas.infraestructure.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface TaskRepositoryJpa extends JpaRepository<TaskEntity, Long> {
    @Query(value = "SELECT * FROM task WHERE id_user = ?1 AND month(date) = ?2 AND year(date) = ?3", nativeQuery = true)
    List<TaskEntity> getTasksByIdUserAndMonthAndYear(Long idUser, String month, String year);

    @Query(value = "SELECT * FROM task WHERE id_user = ?1 AND date = ?2", nativeQuery = true)
    List<TaskEntity> getTasksByIdUserAndDate(Long idUser, LocalDate date);
}
