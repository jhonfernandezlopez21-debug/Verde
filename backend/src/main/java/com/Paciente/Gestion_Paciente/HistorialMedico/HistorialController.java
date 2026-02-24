package com.Paciente.Gestion_Paciente.HistorialMedico;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/historial")
@CrossOrigin(origins = {"https://frontend-equipo-verde.firebaseapp.com/", "http://localhost:3000"})
public class HistorialController {

    @Autowired
    private IHistorialService historialService;

    @GetMapping
    public ResponseEntity<List<HistorialMedicoModels>> getAll(){
        List<HistorialMedicoModels> historiales = historialService.getAll();
        return ResponseEntity.ok(historiales);
    }

    @PostMapping
    public ResponseEntity<HistorialMedicoModels> create(@Valid @RequestBody HistorialMedicoModels payload){
        try {
            HistorialMedicoModels nuevoHistorial = historialService.save(payload);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoHistorial);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<HistorialMedicoModels>> getById(@PathVariable("id") Long id){
        try {
            Optional<HistorialMedicoModels> historial = historialService.getById(id);
            if (historial.isPresent()) {
                return ResponseEntity.ok(historial);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<HistorialMedicoModels> update(@Valid @RequestBody HistorialMedicoModels payload, @PathVariable("id") Long id){
        try {
            HistorialMedicoModels historialActualizado = historialService.update(id, payload);
            return ResponseEntity.ok(historialActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        try {
            boolean ok = historialService.delete(id);
            if(ok) {
                return ResponseEntity.ok("Historial con id " + id + " eliminado exitosamente");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar historial");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
