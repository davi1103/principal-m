package com.arqui.GreenCoom.Eventos.Vistas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventResponse {
    private Long id;
    private String titulo;
    private LocalDate fecha;
    private String descripcion;
    private String link;
    private Long id_Usuario;
}