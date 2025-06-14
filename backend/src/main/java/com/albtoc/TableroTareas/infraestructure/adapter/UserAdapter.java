package com.albtoc.TableroTareas.infraestructure.adapter;

import com.albtoc.TableroTareas.application.ports.out.UserOutService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserAdapter implements UserOutService {
    @Override
    public boolean matchPasswords(String password, String passwordEncoded) {
        return new BCryptPasswordEncoder().matches(password, passwordEncoded);
    }

    @Override
    public String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
