package com.albtoc.TableroTareas.application.ports.out;


public interface UserOutService {
    boolean matchPasswords(String password, String passwordEncoded);

    String encodePassword(String password);
}
