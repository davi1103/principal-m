package com.arqui.GreenCoom.Authentication.Repositorio;

import com.arqui.GreenCoom.Authentication.Entidad.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
}
