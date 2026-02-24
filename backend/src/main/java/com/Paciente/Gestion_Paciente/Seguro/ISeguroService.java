package com.Paciente.Gestion_Paciente.Seguro;

import java.util.List;
import java.util.Optional;

public interface ISeguroService {
    List<SeguroModels> getAll();
    Optional<SeguroModels> getById(Long id);
    SeguroModels save(SeguroModels insurance);
    void delete(Long id);
}

