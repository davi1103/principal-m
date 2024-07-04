package com.arqui.GreenCoom.Authentication.Config;

import com.arqui.GreenCoom.Authentication.Entidad.User;
import com.arqui.GreenCoom.Authentication.Repositorio.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JwtUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);
    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.debug("---loadUserByUsername called.---");
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) {

            return user.get();
            //  return new User(user.get().getEmail(), user.get().getPassword(),true,true,true,true, authorities);
        } else {
            throw new UsernameNotFoundException("User "+email+" not found.");
        }
    }
}