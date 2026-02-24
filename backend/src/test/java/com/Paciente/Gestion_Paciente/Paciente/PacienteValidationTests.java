package com.Paciente.Gestion_Paciente.Paciente;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PacienteValidationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Validator validator;

    @Autowired
    private ObjectMapper objectMapper;

    private PacienteModels pacienteValido;

    @BeforeEach
    public void setUp() {
        pacienteValido = new PacienteModels();
        pacienteValido.setNombre("Juan");
        pacienteValido.setApellido("Pérez");
        pacienteValido.setNumeroDeCelular("3123456789");
        pacienteValido.setEdad("30");
        pacienteValido.setDirrecion("Calle 123 Apto 4");
        pacienteValido.setFechaIngreso("2026-02-16");
        pacienteValido.setCedulaIdentidad("123456");
        pacienteValido.setFechaNacimiento("1996-05-20");
        pacienteValido.setSexo("Masculino");
        pacienteValido.setNacionalidad("Colombia");
        pacienteValido.setEstadoCivil("Soltero");
        pacienteValido.setOcupacion("Ingeniero");
        pacienteValido.setPersonaContacto("María Pérez");
        pacienteValido.setTelefonoContacto("3212345678");
        pacienteValido.setAlergiasConocidas("Penicilina");
        pacienteValido.setMedicamentosActuales("Paracetamol");
        pacienteValido.setMotivoIngreso("Consulta de rutina por dolor en el pecho");
    }

    @Test
    public void testPacienteValido() {
        Set<ConstraintViolation<PacienteModels>> violations = validator.validate(pacienteValido);
        assertTrue(violations.isEmpty(), "El paciente válido no debe tener violaciones");
    }

    @Test
    public void testNombreBlanco() {
        pacienteValido.setNombre("");
        Set<ConstraintViolation<PacienteModels>> violations = validator.validate(pacienteValido);
        assertFalse(violations.isEmpty(), "Debe validar el nombre en blanco");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("nombre no puede estar vacío")));
    }

    @Test
    public void testNombreMuyCorto() {
        pacienteValido.setNombre("J");
        Set<ConstraintViolation<PacienteModels>> violations = validator.validate(pacienteValido);
        assertFalse(violations.isEmpty(), "Debe validar el tamaño del nombre");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("entre 2 y 100 caracteres")));
    }

    @Test
    public void testNumerocelularInvalido() {
        pacienteValido.setNumeroDeCelular("123");
        Set<ConstraintViolation<PacienteModels>> violations = validator.validate(pacienteValido);
        assertFalse(violations.isEmpty(), "Debe validar el formato del teléfono");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("entre 7 y 15 dígitos")));
    }

    @Test
    public void testEdadInvalida() {
        pacienteValido.setEdad("abc");
        Set<ConstraintViolation<PacienteModels>> violations = validator.validate(pacienteValido);
        assertFalse(violations.isEmpty(), "Debe validar que la edad sea numérica");
    }

    @Test
    public void testCedulaInvalida() {
        pacienteValido.setCedulaIdentidad("abc");
        Set<ConstraintViolation<PacienteModels>> violations = validator.validate(pacienteValido);
        assertFalse(violations.isEmpty(), "Debe validar que la cédula sea numérica");
    }

    @Test
    public void testFechaIngresoInvalida() {
        pacienteValido.setFechaIngreso("16-02-2026");
        Set<ConstraintViolation<PacienteModels>> violations = validator.validate(pacienteValido);
        assertFalse(violations.isEmpty(), "Debe validar el formato de fecha YYYY-MM-DD");
    }

    @Test
    public void testSexoInvalido() {
        pacienteValido.setSexo("Desconocido");
        Set<ConstraintViolation<PacienteModels>> violations = validator.validate(pacienteValido);
        assertFalse(violations.isEmpty(), "Debe validar valores válidos de sexo");
    }

    @Test
    public void testEstadoCivilInvalido() {
        pacienteValido.setEstadoCivil("Comprometido");
        Set<ConstraintViolation<PacienteModels>> violations = validator.validate(pacienteValido);
        assertFalse(violations.isEmpty(), "Debe validar valores válidos de estado civil");
    }

    @Test
    public void testCrearPacienteValidoEnControlador() throws Exception {
        String pacienteJson = objectMapper.writeValueAsString(pacienteValido);

        mockMvc.perform(post("/paciente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pacienteJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCrearPacienteInvalidoEnControlador() throws Exception {
        PacienteModels pacienteInvalido = new PacienteModels();
        pacienteInvalido.setNombre(""); // Inválido
        pacienteInvalido.setApellido(""); // Inválido

        String pacienteJson = objectMapper.writeValueAsString(pacienteInvalido);

        mockMvc.perform(post("/paciente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pacienteJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Errores de validación"));
    }
}

