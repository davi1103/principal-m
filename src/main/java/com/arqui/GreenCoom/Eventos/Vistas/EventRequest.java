package com.arqui.GreenCoom.Eventos.Vistas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequest {

    private String titulo;
    private String descripcion;
    private LocalDate fecha;
    private LocalTime hora;
    private String link;
}
