package com.Paciente.Gestion_Paciente.Paciente;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/paciente")
@CrossOrigin(origins = {"https://frontend-equipo-verde.firebaseapp.com/", "http://localhost:3000"})
public class Pacientecontrollers {

    @Autowired
    private IPacienteService pacienteServirces;

    @GetMapping
    public ArrayList<PacienteModels> getPaciente(){
        return this.pacienteServirces.getPaciente();
    }

    @PostMapping
    public ResponseEntity<?> savePaciente(@Valid @RequestBody PacienteModels Paciente, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Errores de validación");
            response.put("detalles", errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        PacienteModels saved = this.pacienteServirces.savePaciente(Paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping(path= "/{id}")
    public Optional<PacienteModels> getPacienteById(@PathVariable("id") Long id){
        return this.pacienteServirces.getByid(id);
    }

    @PutMapping(path= "/{id}")
    public ResponseEntity<?> updatePacienteById(@Valid @RequestBody PacienteModels request, @PathVariable("id") Long id, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Errores de validación");
            response.put("detalles", errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        PacienteModels updated = this.pacienteServirces.updateByID(request, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(path= "/{id}")
    public String deleteById(@PathVariable("id") Long id){
        boolean ok = this.pacienteServirces.deletePaciente(id);
        if(ok){
            return "Este Panciente con id " + id + " Fue Eliminado";
        }else return "Error, Hubo Un Problema";
    }

}
