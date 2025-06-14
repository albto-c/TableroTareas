package com.albtoc.TableroTareas.application.ports.in;

import com.albtoc.TableroTareas.domain.exceptions.UserNotFoundException;
import com.albtoc.TableroTareas.domain.models.User;

public interface UserService {
    User findByUsername(String username) throws UserNotFoundException;

    boolean matchPasswords(String password, String passwordEncoded);

    String encodePassword(String password);
}
