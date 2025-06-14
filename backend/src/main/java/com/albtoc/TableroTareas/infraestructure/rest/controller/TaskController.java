package com.albtoc.TableroTareas.infraestructure.rest.controller;

import com.albtoc.TableroTareas.application.ports.in.TaskService;
import com.albtoc.TableroTareas.application.ports.in.UserService;
import com.albtoc.TableroTareas.application.ports.out.JwtOutService;
import com.albtoc.TableroTareas.domain.dtos.TaskDto;
import com.albtoc.TableroTareas.domain.exceptions.HeaderAuthorizationException;
import com.albtoc.TableroTareas.domain.exceptions.TokenException;
import com.albtoc.TableroTareas.domain.exceptions.UserNotFoundException;
import com.albtoc.TableroTareas.domain.models.Task;
import com.albtoc.TableroTareas.domain.models.TaskResponse;
import com.albtoc.TableroTareas.domain.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;
    private final JwtOutService jwtOutService;

    public TaskController(TaskService taskService, UserService userService, JwtOutService jwtOutService) {
        this.taskService = taskService;
        this.userService = userService;
        this.jwtOutService = jwtOutService;
    }

    @GetMapping("/byMonthAndYear/{month}/{year}")
    public ResponseEntity<?> getTasksByMonthAndYear(@PathVariable String month, @PathVariable String year, @RequestHeader String authorization) throws HeaderAuthorizationException, TokenException, UserNotFoundException {
        String token = jwtOutService.getTokenOfHeaderAuthorization(authorization);
        String username = jwtOutService.getUsernameOfToken(token);
        User user = userService.findByUsername(username);
        Optional<List<Task>> tasksList = taskService.getTasksByIdUserAndMonthAndYear(user.getId(), month, year);

        return tasksList.isPresent() ? ResponseEntity.ok(new TaskResponse(tasksList.get())) : ResponseEntity.notFound().build();
    }

    @GetMapping("/byDate/{date}")
    public ResponseEntity<?> getTaskByDate(@PathVariable LocalDate date, @RequestHeader String authorization) throws HeaderAuthorizationException, TokenException, UserNotFoundException {
        String token = jwtOutService.getTokenOfHeaderAuthorization(authorization);
        String username = jwtOutService.getUsernameOfToken(token);
        User user = userService.findByUsername(username);
        Optional<List<Task>> tasksList = taskService.getTasksByIdUserAndDate(user.getId(), date);

        return tasksList.isPresent() ? ResponseEntity.ok(new TaskResponse(tasksList.get())) : ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody TaskDto taskDto, @RequestHeader String authorization) throws HeaderAuthorizationException, TokenException, UserNotFoundException {
        String token = jwtOutService.getTokenOfHeaderAuthorization(authorization);
        String username = jwtOutService.getUsernameOfToken(token);

        return taskService.createTask(taskDto, username) != null ? new ResponseEntity<>(HttpStatus.CREATED) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{idTask}/{idTaskStatus}")
    public ResponseEntity<?> updateTaskStatus(@PathVariable Long idTask, @PathVariable Long idTaskStatus) {
        return taskService.updateTaskStatus(idTask, idTaskStatus) != null ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
