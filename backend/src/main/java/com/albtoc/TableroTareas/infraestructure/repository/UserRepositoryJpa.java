package com.albtoc.TableroTareas.infraestructure.repository;

import com.albtoc.TableroTareas.infraestructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryJpa extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
