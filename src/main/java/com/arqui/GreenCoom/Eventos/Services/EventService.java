package com.arqui.GreenCoom.Eventos.Services;

import com.arqui.GreenCoom.Authentication.Entidad.User;
import com.arqui.GreenCoom.Authentication.Repositorio.UserRepository;
import com.arqui.GreenCoom.Eventos.Entidad.Event;
import com.arqui.GreenCoom.Eventos.Repositorio.EventRepository;
import com.arqui.GreenCoom.Eventos.Vistas.EventRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;


    public Event crearEvento (EventRequest request){
        Event event = new Event();

        event.setTitulo(request.getTitulo());
        event.setDescripcion(request.getDescripcion());
        event.setFecha(request.getFecha());
        event.setHora(request.getHora());
        event.setLink(request.getLink());

        //Obtencion del usuario//
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("Usuario no encontrado"));

        event.setUser(user);
        return  eventRepository.save(event);
    }

    public List<Event> listar (){
        return eventRepository.findAllByOrderByFechaDesc();
    }
}
