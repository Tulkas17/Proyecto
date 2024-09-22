package cr.ac.una.springbootaopmaven.controller;

import cr.ac.una.springbootaopmaven.entity.Persona;
import cr.ac.una.springbootaopmaven.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class PersonaController {

    @Autowired
    PersonaRepository personaRepository;

    @GetMapping("/persona")
    ResponseEntity <List<Persona>> getPersonas(){

        return ResponseEntity.ok(personaRepository.findAll());
    }
    @PostMapping("persona")
    ResponseEntity<Persona> savePersona(@RequestBody Persona persona) {
        return Optional.ofNullable(persona)
                .filter(p -> p.getNombre() != null && !p.getNombre().isEmpty())
                .filter(p -> p.getEdad() != null && p.getEdad() >= 0)
                .map(p -> {

                    return personaRepository.save(p);
                })
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping ("persona")
    ResponseEntity<Persona> updatePersona(@RequestBody  Persona persona){
        return Optional.ofNullable(persona)
                .filter(p -> p.getId() != null && personaRepository.existsById(p.getId()))
                .filter(p -> p.getNombre() != null && !p.getNombre().isEmpty())
                .filter(p -> p.getEdad() != null && p.getEdad() >= 0)
                .map(p -> {
                    Persona personaExistente = personaRepository.findById(p.getId()).orElse(null);
                    assert personaExistente != null;
                    personaExistente.setNombre(p.getNombre());
                    personaExistente.setEdad(p.getEdad());
                    return personaRepository.save(personaExistente);
                })
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.badRequest().build());
        }

    @DeleteMapping("persona/{id}")
    ResponseEntity<Void> deletePersona(@PathVariable Long id) {
        return Optional.ofNullable(id)
                .filter(personaRepository::existsById)
                .map(personaId -> {
                    personaRepository.deleteById(personaId);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("persona/{id}")
    ResponseEntity<Persona> getPersona(@PathVariable Long id) {
        return personaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
