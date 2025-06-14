package com.albtoc.TableroTareas.application.services;

import com.albtoc.TableroTareas.application.ports.in.AuthService;
import com.albtoc.TableroTareas.application.ports.in.UserService;
import com.albtoc.TableroTareas.application.ports.out.JwtOutService;
import com.albtoc.TableroTareas.application.ports.out.UserRepositoryService;
import com.albtoc.TableroTareas.domain.dtos.LoginDto;
import com.albtoc.TableroTareas.domain.exceptions.IncorrectPasswordException;
import com.albtoc.TableroTareas.domain.exceptions.UserNotFoundException;
import com.albtoc.TableroTareas.domain.models.User;

import java.util.Optional;

public class AuthServiceImpl implements AuthService {
    private final JwtOutService jwtOutService;
    private final UserRepositoryService userRepositoryService;
    private final UserService userService;

    public AuthServiceImpl(JwtOutService jwtOutService, UserRepositoryService userRepositoryService, UserService userService) {
        this.jwtOutService = jwtOutService;
        this.userRepositoryService = userRepositoryService;
        this.userService = userService;
    }

    @Override
    public String login(LoginDto loginDto) throws UserNotFoundException, IncorrectPasswordException {
        Optional<User> user = userRepositoryService.findByUsername(loginDto.getUsername());

        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }

        if (!userService.matchPasswords(loginDto.getPassword(), user.get().getPassword())) {
            throw new IncorrectPasswordException();
        }

        return jwtOutService.generateToken(user.get().getUsername(), user.get().getAuthority());
    }

    @Override
    public boolean register(LoginDto loginDto) {
        if (loginDto == null) {
            return false;
        }
        
        User user = new User();
        user.setUsername(loginDto.getUsername());
        user.setPassword(userService.encodePassword(loginDto.getPassword()));
        return userRepositoryService.save(user) != null;
    }
}
