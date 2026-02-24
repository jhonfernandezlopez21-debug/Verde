package com.Paciente.Gestion_Paciente.Factura;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/invoice")
@CrossOrigin(origins = {"https://frontend-equipo-verde.firebaseapp.com/", "http://localhost:3000"})
public class FacturaController {

    @Autowired
    private IFacturaService FacturaService;

    @GetMapping
    public ResponseEntity<List<FacturaModels>> getAll(){
        List<FacturaModels> facturas = FacturaService.getAll();
        return ResponseEntity.ok(facturas);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<FacturaModels>> getById(@PathVariable Long id){
        Optional<FacturaModels> factura = FacturaService.getById(id);
        if (factura.isPresent()) {
            return ResponseEntity.ok(factura);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<FacturaModels> create(@Valid @RequestBody FacturaModels invoice){
        try {
            FacturaModels nuevaFactura = FacturaService.save(invoice);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaFactura);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<FacturaModels> update(@Valid @RequestBody FacturaModels invoice, @PathVariable Long id){
        try {
            invoice.setId(id);
            FacturaModels facturaActualizada = FacturaService.save(invoice);
            return ResponseEntity.ok(facturaActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        try {
            FacturaService.delete(id);
            return ResponseEntity.ok("Factura eliminada exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
