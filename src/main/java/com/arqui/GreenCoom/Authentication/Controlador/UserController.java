package com.arqui.GreenCoom.Authentication.Controlador;

import com.arqui.GreenCoom.Authentication.Config.AplicationConfig;
import com.arqui.GreenCoom.Authentication.Entidad.User;
import com.arqui.GreenCoom.Authentication.Repositorio.UserRepository;
import com.arqui.GreenCoom.Authentication.Vistas.LoginRequest;
import com.arqui.GreenCoom.Authentication.Vistas.LoginResponse;
import com.arqui.GreenCoom.Authentication.Vistas.UserRequest;
import com.arqui.GreenCoom.Authentication.Vistas.UserResponse;
import com.arqui.GreenCoom.Authentication.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody UserRequest request){
        Optional<User> usuario = userRepository.findByEmail(request.getEmail());
        if(usuario.isPresent()){
            return ResponseEntity.badRequest().body("El email ingresado ya esta registrado");
        }
        else{
            User newUser = new User(request);
            newUser.setPassword(AplicationConfig.passwordEncoder().encode(request.getPassword()));
            userRepository.save(newUser);
            return ResponseEntity.ok().body("Usuario registrado correctamente");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginRequest request){
        System.out.println("Inicio del metodo login");
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if(user.isEmpty()){
            return ResponseEntity.badRequest().body("Correo ingresado no esta registrado");
        }
        else{
            if(passwordEncoder.matches(request.getPassword(), user.get().getPassword())){
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
                LoginResponse response = new LoginResponse(jwtService.getToken(user.get()));
                return ResponseEntity.ok().body(response);

            }
            else{
                return ResponseEntity.badRequest().body("Contraseña Incorrecta");
            }

        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserResponse>> toList(){
        List<User> users = userRepository.findAll();
        List<UserResponse> usersResponse = users.stream().map(usuario ->
                        new UserResponse(usuario.getId(), usuario.getNombre(), usuario.getEmail()))
                .toList();
        return ResponseEntity.ok(usersResponse);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()){
            return ResponseEntity.badRequest().body("El usuario no existe");
        }
        else{
            User user1 = user.get();
            UserResponse usersResponse = new UserResponse(user1.getId(), user1.getNombre(), user1.getEmail());
            return ResponseEntity.ok().body(usersResponse);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()){
            return ResponseEntity.badRequest().body("El usuario no existe");
        }
        else{
            userRepository.deleteById(id);
            return ResponseEntity.ok().body("Usuario eliminado correctamente");
        }
    }
}
