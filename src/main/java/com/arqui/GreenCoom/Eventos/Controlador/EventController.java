package com.arqui.GreenCoom.Eventos.Controlador;

import com.arqui.GreenCoom.Authentication.Repositorio.UserRepository;
import com.arqui.GreenCoom.Eventos.Entidad.Event;
import com.arqui.GreenCoom.Eventos.Repositorio.EventRepository;
import com.arqui.GreenCoom.Eventos.Services.EventService;
import com.arqui.GreenCoom.Eventos.Vistas.EventRequest;
import com.arqui.GreenCoom.Eventos.Vistas.EventResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;
    @Autowired
    private EventRepository eventRepository;

    @PostMapping(path = "/create", consumes = "application/json")
    public ResponseEntity<?> create(@RequestBody EventRequest request){
        try {
            eventService.crearEvento(request);
            return ResponseEntity.ok().body(Map.of("message", "Evento creado con éxito."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Error al crear el evento: " + e.getMessage()));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Event>> listAll(){
        List<Event> events = eventService.listar();
        return ResponseEntity.ok().body(events);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findByID(@PathVariable Long id){
        Optional<Event> evento = eventRepository.findById(id);
        if (evento.isEmpty()){
            return ResponseEntity.badRequest().body("El evento no existe");
        }
        else{
            Event event = evento.get();
            EventResponse eventsResponse = new EventResponse(
                    event.getId(),
                    event.getTitulo(),
                    event.getFecha(),
                    event.getDescripcion(),
                    event.getLink(),
                    event.getUser().getId());
            return ResponseEntity.ok().body(eventsResponse);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()){
            return ResponseEntity.badRequest().body("El evento no existe");
        }
        else{
            eventRepository.deleteById(id);
            return ResponseEntity.ok().body("Evento eliminado correctamente");
        }
    }


}
