package com.Paciente.Gestion_Paciente.Seguro;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class SeguroServiceTests {

    @Autowired
    private SeguroService seguroService;

    @Autowired
    private ISeguroRepository seguroRepository;

    private SeguroModels completarSeguroBasico() {
        SeguroModels s = new SeguroModels();
        s.setCompania("CompaniaTest");
        s.setNumeroPoliza("POL123");
        s.setCobertura("Cobertura genérica de prueba");
        s.setDeducible("0.00");
        s.setLimiteCobertura("1000.00");
        return s;
    }

    @Test
    public void testSaveAndGetById() {
        SeguroModels s = completarSeguroBasico();
        SeguroModels saved = seguroService.save(s);

        assertNotNull(saved.getId());

        Optional<SeguroModels> opt = seguroService.getById(saved.getId());
        assertTrue(opt.isPresent());
        assertEquals("CompaniaTest", opt.get().getCompania());
    }

    @Test
    public void testDeleteInsuranceRemovesIt() {
        SeguroModels s = completarSeguroBasico();
        SeguroModels saved = seguroRepository.save(s);

        // ensure present
        Optional<SeguroModels> before = seguroRepository.findById(saved.getId());
        assertTrue(before.isPresent());

        seguroService.delete(saved.getId());

        Optional<SeguroModels> after = seguroRepository.findById(saved.getId());
        assertTrue(after.isEmpty());
    }
}
