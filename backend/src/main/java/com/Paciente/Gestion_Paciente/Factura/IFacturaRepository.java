package com.Paciente.Gestion_Paciente.Factura;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFacturaRepository extends JpaRepository<FacturaModels, Long> {
}

