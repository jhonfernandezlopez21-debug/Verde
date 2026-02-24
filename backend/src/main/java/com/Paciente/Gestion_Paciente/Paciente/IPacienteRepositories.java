package com.Paciente.Gestion_Paciente.Paciente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPacienteRepositories extends JpaRepository<PacienteModels, Long> {


}
