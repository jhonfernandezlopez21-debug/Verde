package com.Paciente.Gestion_Paciente.HistorialMedico;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class HistorialServiceTests {

    @Autowired
    private HistorialService historialService;

    @Autowired
    private IHistorialRepository historialRepository;

    @Test
    public void testSaveAndGetById() {
        HistorialMedicoModels h = new HistorialMedicoModels();
        h.setAntecedentesMedicos("Ninguno");
        HistorialMedicoModels saved = historialService.save(h);
        assertNotNull(saved.getId());

        Optional<HistorialMedicoModels> opt = historialService.getById(saved.getId());
        assertTrue(opt.isPresent());
        assertEquals("Ninguno", opt.get().getAntecedentesMedicos());
    }

    @Test
    public void testUpdateExisting() {
        HistorialMedicoModels h = new HistorialMedicoModels();
        h.setAntecedentesMedicos("A");
        HistorialMedicoModels saved = historialRepository.save(h);

        HistorialMedicoModels updated = new HistorialMedicoModels();
        updated.setAntecedentesMedicos("B");

        HistorialMedicoModels result = historialService.update(saved.getId(), updated);
        assertEquals("B", result.getAntecedentesMedicos());
    }
}
