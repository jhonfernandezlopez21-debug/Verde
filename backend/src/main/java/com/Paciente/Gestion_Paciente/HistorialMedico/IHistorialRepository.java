package com.Paciente.Gestion_Paciente.HistorialMedico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHistorialRepository extends JpaRepository<HistorialMedicoModels, Long> {

}
