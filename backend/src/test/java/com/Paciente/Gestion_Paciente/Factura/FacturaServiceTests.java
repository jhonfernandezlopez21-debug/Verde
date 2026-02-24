package com.Paciente.Gestion_Paciente.Factura;

import com.Paciente.Gestion_Paciente.Paciente.PacienteModels;
import com.Paciente.Gestion_Paciente.Paciente.IPacienteRepositories;
import com.Paciente.Gestion_Paciente.Seguro.SeguroModels;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class FacturaServiceTests {

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private IFacturaRepository facturaRepository;

    @Autowired
    private IPacienteRepositories pacienteRepository;

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
    public void testSaveConCoberturaPorcentaje() {
        PacienteModels p = new PacienteModels();
        completarPacienteRequerido(p);
        SeguroModels s = new SeguroModels();
        s.setCobertura("50%");
        s.setCompania("CompTest");
        s.setNumeroPoliza("POL50");
        s.setDeducible("0.00");
        s.setLimiteCobertura("100.00");
        p.setInsurance(s);
        pacienteRepository.save(p);

        FacturaModels f = new FacturaModels();
        f.setDescripcion("Factura de prueba por servicios");
        f.setEstado("Pendiente");
        f.setSubtotal(new BigDecimal("200.00"));
        f.setPaciente(p);

        FacturaModels saved = facturaService.save(f);
        assertNotNull(saved.getId());
        assertEquals(0, saved.getTotal().compareTo(new BigDecimal("100.00")));
    }

    @Test
    public void testSaveConLimiteNumerico() {
        PacienteModels p = new PacienteModels();
        completarPacienteRequerido(p);
        SeguroModels s = new SeguroModels();
        s.setLimiteCobertura("30");
        s.setCompania("CompTest");
        s.setNumeroPoliza("POL30");
        s.setDeducible("0.00");
        p.setInsurance(s);
        pacienteRepository.save(p);

        FacturaModels f = new FacturaModels();
        f.setDescripcion("Factura de prueba con límite");
        f.setEstado("Pendiente");
        f.setSubtotal(new BigDecimal("80.00"));
        f.setPaciente(p);

        FacturaModels saved = facturaService.save(f);
        assertNotNull(saved.getId());
        assertEquals(0, saved.getTotal().compareTo(new BigDecimal("50.00")));
    }

    @Test
    public void testSaveUsesExistingPacienteIfIdProvided() {
        PacienteModels p = new PacienteModels();
        completarPacienteRequerido(p);
        p.setNombre("Existente");
        PacienteModels savedP = pacienteRepository.save(p);

        PacienteModels ref = new PacienteModels();
        ref.setIdPaciente(savedP.getIdPaciente());

        FacturaModels f = new FacturaModels();
        f.setDescripcion("Factura con paciente existente");
        f.setEstado("Pendiente");
        f.setSubtotal(new BigDecimal("10.00"));
        f.setPaciente(ref);

        FacturaModels saved = facturaService.save(f);
        assertNotNull(saved.getId());
        Optional<FacturaModels> opt = facturaRepository.findById(saved.getId());
        assertTrue(opt.isPresent());
        assertEquals(savedP.getIdPaciente(), opt.get().getPaciente().getIdPaciente());
    }
}
