package com.Paciente.Gestion_Paciente.HistorialMedico;

import java.util.List;
import java.util.Optional;

public interface IHistorialService {
    List<HistorialMedicoModels> getAll();
    Optional<HistorialMedicoModels> getById(Long id);
    HistorialMedicoModels save(HistorialMedicoModels hist);
    HistorialMedicoModels update(Long id, HistorialMedicoModels request);
    boolean delete(Long id);
}

