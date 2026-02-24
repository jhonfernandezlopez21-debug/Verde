package com.Paciente.Gestion_Paciente.Factura;

import java.util.List;
import java.util.Optional;

public interface IFacturaService {
    List<FacturaModels> getAll();
    Optional<FacturaModels> getById(Long id);
    FacturaModels save(FacturaModels invoice);
    void delete(Long id);
}

