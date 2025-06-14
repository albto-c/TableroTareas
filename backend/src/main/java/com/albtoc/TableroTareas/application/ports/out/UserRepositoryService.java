package com.albtoc.TableroTareas.application.ports.out;

import com.albtoc.TableroTareas.domain.models.User;

import java.util.Optional;

public interface UserRepositoryService {
    Optional<User> findByUsername(String username);

    User save(User user);
}

