package com.albtoc.TableroTareas.application.ports.in;

import com.albtoc.TableroTareas.domain.dtos.LoginDto;
import com.albtoc.TableroTareas.domain.exceptions.IncorrectPasswordException;
import com.albtoc.TableroTareas.domain.exceptions.UserNotFoundException;

public interface AuthService {
    String login(LoginDto loginDto) throws UserNotFoundException, IncorrectPasswordException;

    boolean register(LoginDto loginDto);
}
