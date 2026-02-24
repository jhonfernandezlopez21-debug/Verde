package com.Paciente.Gestion_Paciente.Seguro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISeguroRepository extends JpaRepository<SeguroModels, Long> {

}

