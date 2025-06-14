package com.albtoc.TableroTareas.infraestructure.mapper;

import com.albtoc.TableroTareas.domain.models.User;
import com.albtoc.TableroTareas.infraestructure.entity.UserEntity;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserMapper {
    public User entityToModel(UserEntity userEntity) {
        if (userEntity == null) {
            throw new NullPointerException();
        }

        User user = new User();
        user.setId(userEntity.getId());
        user.setUsername(userEntity.getUsername());
        user.setPassword(userEntity.getPassword());
        String authority = userEntity.getAuthorities().stream().toList().get(0).getAuthority();
        user.setAuthority(authority);
        return user;
    }

    public UserEntity modelToEntity(User user) {
        if (user == null) {
            throw new NullPointerException();
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        return userEntity;
    }
}
