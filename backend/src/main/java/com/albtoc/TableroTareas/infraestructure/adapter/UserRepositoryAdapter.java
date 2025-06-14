package com.albtoc.TableroTareas.infraestructure.adapter;

import com.albtoc.TableroTareas.application.ports.out.UserRepositoryService;
import com.albtoc.TableroTareas.infraestructure.mapper.UserMapper;
import com.albtoc.TableroTareas.domain.models.User;
import com.albtoc.TableroTareas.infraestructure.entity.UserEntity;
import com.albtoc.TableroTareas.infraestructure.repository.UserRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepositoryService {
    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Optional<User> findByUsername(String username) {
        UserEntity userEntity = userRepositoryJpa.findByUsername(username);

        if (userEntity == null) {
            return Optional.empty();
        }
        return Optional.of(userMapper.entityToModel(userEntity));
    }

    @Override
    public User save(User user) {
        if (user == null) {
            throw new NullPointerException();
        }
        UserEntity userEntity = userMapper.modelToEntity(user);
        return userMapper.entityToModel(userRepositoryJpa.save(userEntity));
    }
}
