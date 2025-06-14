package com.albtoc.TableroTareas;

import com.albtoc.TableroTareas.infraestructure.entity.TaskStatusEntity;
import com.albtoc.TableroTareas.infraestructure.repository.TaskStatusRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class TableroTareasApplication {

    @Autowired
    private TaskStatusRepositoryJpa taskStatusRepositoryJpa;

    public static void main(String[] args) {
        SpringApplication.run(TableroTareasApplication.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            Optional<TaskStatusEntity> taskStatus1 = taskStatusRepositoryJpa.findById(1L);
            Optional<TaskStatusEntity> taskStatus2 = taskStatusRepositoryJpa.findById(2L);

            if (taskStatus1.isEmpty()) {
                taskStatusRepositoryJpa.save(new TaskStatusEntity(1L, "todo"));
            }

            if (taskStatus2.isEmpty()) {
                taskStatusRepositoryJpa.save(new TaskStatusEntity(2L, "finished"));
            }
        };
    }
}