package com.Paciente.Gestion_Paciente.Paciente;

import java.util.ArrayList;
import java.util.Optional;

public interface IPacienteService {
    ArrayList<PacienteModels> getPaciente();
    PacienteModels savePaciente(PacienteModels paciente);
    Optional<PacienteModels> getByid(Long id);
    PacienteModels updateByID(PacienteModels request, Long id);
    Boolean deletePaciente(Long id);
}

