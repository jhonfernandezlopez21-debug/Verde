package com.Paciente.Gestion_Paciente.Paciente;

import com.Paciente.Gestion_Paciente.Seguro.SeguroModels;
import com.Paciente.Gestion_Paciente.Seguro.ISeguroRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PacienteServiceTests {

    @Autowired
    private PacienteServirces pacienteServirces;

    @Autowired
    private IPacienteRepositories pacienteRepositories;

    private void completarPacienteRequerido(PacienteModels p) {
        p.setNombre("Test");
        p.setApellido("Apellido");
        p.setNumeroDeCelular("1234567");
        p.setEdad("30");
        p.setDirrecion("Calle 1");
        p.setFechaIngreso("2024-01-01");
        p.setCedulaIdentidad("12345678");
        p.setFechaNacimiento("1994-01-01");
        p.setSexo("M");
        p.setNacionalidad("Nacionalidad");
        p.setEstadoCivil("Soltero");
        p.setPersonaContacto("Contacto");
        p.setTelefonoContacto("7654321");
        p.setMotivoIngreso("Motivo de prueba");
    }

    @Test
    public void testSavePacienteCreatesNewInsuranceWhenNoId() {
        PacienteModels p = new PacienteModels();
        completarPacienteRequerido(p);
        p.setNombre("Juan");
        SeguroModels ins = new SeguroModels();
        ins.setCompania("Seguros SA");
        ins.setCobertura("Cobertura de prueba 50%");
        ins.setNumeroPoliza("NP12345");
        ins.setDeducible("0.00");
        ins.setLimiteCobertura("1000.00");
        p.setInsurance(ins);

        PacienteModels saved = pacienteServirces.savePaciente(p);

        assertNotNull(saved);
        assertTrue(saved.getTieneSeguro());
        assertNotNull(saved.getInsurance());
        assertNotNull(saved.getInsurance().getId());
    }

    @Test
    public void testGetPacienteReturnsList() {
        // ensure at least one
        PacienteModels p = new PacienteModels();
        completarPacienteRequerido(p);
        p.setNombre("Maria");
        pacienteRepositories.save(p);

        ArrayList<PacienteModels> list = pacienteServirces.getPaciente();
        assertNotNull(list);
        assertFalse(list.isEmpty());
    }

    @Test
    public void testDeletePacienteReturnsTrueWhenExists() {
        PacienteModels p = new PacienteModels();
        completarPacienteRequerido(p);
        p.setNombre("Ana");
        PacienteModels saved = pacienteRepositories.save(p);

        Boolean deleted = pacienteServirces.deletePaciente(saved.getIdPaciente());
        assertTrue(deleted);
        Optional<PacienteModels> opt = pacienteRepositories.findById(saved.getIdPaciente());
        assertTrue(opt.isEmpty());
    }
}
