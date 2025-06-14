package com.albtoc.TableroTareas.infraestructure.config;

import com.albtoc.TableroTareas.application.ports.in.AuthService;
import com.albtoc.TableroTareas.application.ports.in.TaskService;
import com.albtoc.TableroTareas.application.ports.in.UserService;
import com.albtoc.TableroTareas.application.ports.out.JwtOutService;
import com.albtoc.TableroTareas.application.ports.out.TaskRepositoryService;
import com.albtoc.TableroTareas.application.ports.out.UserOutService;
import com.albtoc.TableroTareas.application.ports.out.UserRepositoryService;
import com.albtoc.TableroTareas.application.services.AuthServiceImpl;
import com.albtoc.TableroTareas.application.services.TaskServiceImpl;
import com.albtoc.TableroTareas.application.services.UserServiceImpl;
import com.albtoc.TableroTareas.infraestructure.adapter.JwtAdapter;
import com.albtoc.TableroTareas.infraestructure.adapter.TaskRepositoryAdapter;
import com.albtoc.TableroTareas.infraestructure.adapter.UserAdapter;
import com.albtoc.TableroTareas.infraestructure.adapter.UserRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public AuthService authService(JwtOutService jwtOutService, UserRepositoryService userRepositoryService, UserService userService) {
        return new AuthServiceImpl(jwtOutService, userRepositoryService, userService);
    }

    @Bean
    public JwtOutService jwtOutService() {
        return new JwtAdapter();
    }

    @Bean
    public UserRepositoryService userRepositoryService() {
        return new UserRepositoryAdapter();
    }

    @Bean
    public UserOutService userOutService() {
        return new UserAdapter();
    }

    @Bean
    public UserService userService(UserRepositoryService userRepositoryService, UserOutService userOutService) {
        return new UserServiceImpl(userRepositoryService, userOutService);
    }

    @Bean
    public TaskRepositoryService taskRepositoryService() {
        return new TaskRepositoryAdapter();
    }

    @Bean
    public TaskService taskService(TaskRepositoryService taskRepositoryService, UserRepositoryService userRepositoryService) {
        return new TaskServiceImpl(taskRepositoryService, userRepositoryService);
    }
}
