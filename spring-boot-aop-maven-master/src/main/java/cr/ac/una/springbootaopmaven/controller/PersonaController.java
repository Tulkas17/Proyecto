package cr.ac.una.springbootaopmaven.controller;

import cr.ac.una.springbootaopmaven.entity.Persona;
import cr.ac.una.springbootaopmaven.repository.PersonaRepository;
import cr.ac.una.springbootaopmaven.serviceInterface.IServicioProcesador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PersonaController {

    @Autowired
    PersonaRepository personaRepository;

    @Autowired
    IServicioProcesador servicioProcesador; // Inyección del servicio

    // Endpoints relacionados con Persona (ya existentes)
    @GetMapping("/persona")
    ResponseEntity<List<Persona>> getPersonas() {
        return ResponseEntity.ok(personaRepository.findAll());
    }

    @PostMapping("/persona")
    ResponseEntity<Persona> savePersona(@RequestBody Persona persona) {
        return Optional.ofNullable(persona)
                .filter(p -> p.getNombre() != null && !p.getNombre().isEmpty())
                .filter(p -> p.getEdad() != null && p.getEdad() >= 0)
                .map(p -> personaRepository.save(p))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/persona")
    ResponseEntity<Persona> updatePersona(@RequestBody Persona persona) {
        return Optional.ofNullable(persona)
                .filter(p -> p.getId() != null && personaRepository.existsById(p.getId()))
                .filter(p -> p.getNombre() != null && !p.getNombre().isEmpty())
                .filter(p -> p.getEdad() != null && p.getEdad() >= 0)
                .map(p -> {
                    Persona personaExistente = personaRepository.findById(p.getId()).orElse(null);
                    if (personaExistente != null) {
                        personaExistente.setNombre(p.getNombre());
                        personaExistente.setEdad(p.getEdad());
                        return personaRepository.save(personaExistente);
                    }
                    return null;
                })
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/persona/{id}")
    ResponseEntity<Void> deletePersona(@PathVariable Long id) {
        return Optional.ofNullable(id)
                .filter(personaRepository::existsById)
                .map(personaId -> {
                    personaRepository.deleteById(personaId);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/persona/{id}")
    ResponseEntity<Persona> getPersona(@PathVariable Long id) {
        return personaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Nuevos endpoints para los reportes
    @GetMapping("/reporte/error")
    ResponseEntity<Map<String, Object>> generarReporteError() {
        return ResponseEntity.ok(servicioProcesador.generarReporteError());
    }

    @GetMapping("/reporte/endpoint")
    ResponseEntity<Map<String, Object>> generarReporteEndPoint() {
        return ResponseEntity.ok(servicioProcesador.generarReporteEndPoint());
    }

    @GetMapping("/reporte/tiempo")
    ResponseEntity<Map<String, Object>> generarRespuestaTiempoReporte() {
        return ResponseEntity.ok(servicioProcesador.generarRespuestaTiempoReporte());
    }

    @GetMapping("/reporte/status")
    ResponseEntity<Map<String, Object>> generarReporteEstatusAplicacion() {
        return ResponseEntity.ok(servicioProcesador.generarReporteEstatusAplicacion());
    }

    @GetMapping("/reporte/errorCritico")
    ResponseEntity<Map<String, Object>> generarReporteErrorCritico() {
        return ResponseEntity.ok(servicioProcesador.generarReporteErrorCritico());
    }
}
