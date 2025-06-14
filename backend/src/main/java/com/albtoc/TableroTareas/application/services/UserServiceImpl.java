package com.albtoc.TableroTareas.application.services;

import com.albtoc.TableroTareas.application.ports.in.UserService;
import com.albtoc.TableroTareas.application.ports.out.UserOutService;
import com.albtoc.TableroTareas.application.ports.out.UserRepositoryService;
import com.albtoc.TableroTareas.domain.exceptions.UserNotFoundException;
import com.albtoc.TableroTareas.domain.models.User;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserRepositoryService userRepositoryService;
    private final UserOutService userOutService;

    public UserServiceImpl(UserRepositoryService userRepositoryService, UserOutService userOutService) {
        this.userRepositoryService = userRepositoryService;
        this.userOutService = userOutService;
    }

    @Override
    public User findByUsername(String username) throws UserNotFoundException {
        Optional<User> user = userRepositoryService.findByUsername(username);

        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        return user.get();
    }

    @Override
    public boolean matchPasswords(String password, String passwordEncoded) {
        return userOutService.matchPasswords(password, passwordEncoded);
    }

    @Override
    public String encodePassword(String password) {
        return userOutService.encodePassword(password);
    }
}
