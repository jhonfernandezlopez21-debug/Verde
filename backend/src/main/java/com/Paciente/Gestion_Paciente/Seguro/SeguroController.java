package com.Paciente.Gestion_Paciente.Seguro;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/insurance")
@CrossOrigin(origins = {"https://frontend-equipo-verde.firebaseapp.com/", "http://localhost:3000"})
public class SeguroController {

    @Autowired
    private ISeguroService seguroService;

    @GetMapping
    public ResponseEntity<List<SeguroModels>> getAll(){
        List<SeguroModels> seguros = seguroService.getAll();
        return ResponseEntity.ok(seguros);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<SeguroModels>> getById(@PathVariable Long id){
        Optional<SeguroModels> seguro = seguroService.getById(id);
        if (seguro.isPresent()) {
            return ResponseEntity.ok(seguro);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<SeguroModels> create(@Valid @RequestBody SeguroModels seguro){
        try {
            SeguroModels nuevoSeguro = seguroService.save(seguro);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoSeguro);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<SeguroModels> update(@Valid @RequestBody SeguroModels seguro, @PathVariable Long id){
        try {
            seguro.setId(id);
            SeguroModels seguroActualizado = seguroService.save(seguro);
            return ResponseEntity.ok(seguroActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        try {
            seguroService.delete(id);
            return ResponseEntity.ok("Seguro eliminado exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
