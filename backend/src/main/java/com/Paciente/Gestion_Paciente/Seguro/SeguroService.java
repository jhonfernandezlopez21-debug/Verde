package com.Paciente.Gestion_Paciente.Seguro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeguroService implements ISeguroService {

    @Autowired
    private ISeguroRepository SeguroRepository;

    public List<SeguroModels> getAll(){
        return SeguroRepository.findAll();
    }

    public Optional<SeguroModels> getById(Long id){
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del seguro debe ser un número positivo");
        }
        return SeguroRepository.findById(id);
    }

    public SeguroModels save(SeguroModels insurance){
        if (insurance == null) {
            throw new IllegalArgumentException("El objeto Seguro no puede ser nulo");
        }
        if (insurance.getCompania() == null || insurance.getCompania().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la compañía es obligatorio");
        }
        if (insurance.getNumeroPoliza() == null || insurance.getNumeroPoliza().trim().isEmpty()) {
            throw new IllegalArgumentException("El número de póliza es obligatorio");
        }
        if (insurance.getCobertura() == null || insurance.getCobertura().trim().isEmpty()) {
            throw new IllegalArgumentException("La cobertura es obligatoria");
        }
        if (insurance.getDeducible() == null || insurance.getDeducible().trim().isEmpty()) {
            throw new IllegalArgumentException("El deducible es obligatorio");
        }
        if (insurance.getLimiteCobertura() == null || insurance.getLimiteCobertura().trim().isEmpty()) {
            throw new IllegalArgumentException("El límite de cobertura es obligatorio");
        }

        return SeguroRepository.save(insurance);
    }

    public void delete(Long id){
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del seguro debe ser un número positivo");
        }
        if (!SeguroRepository.existsById(id)) {
            throw new IllegalArgumentException("El seguro con ID " + id + " no existe");
        }
        SeguroRepository.deleteById(id);
    }
}
