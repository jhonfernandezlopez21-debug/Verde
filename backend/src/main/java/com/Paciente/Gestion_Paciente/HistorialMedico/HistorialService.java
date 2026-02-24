package com.Paciente.Gestion_Paciente.HistorialMedico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistorialService implements IHistorialService {

    @Autowired
    private IHistorialRepository historialRepository;

    public List<HistorialMedicoModels> getAll() {
        return historialRepository.findAll();
    }

    public Optional<HistorialMedicoModels> getById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del historial debe ser un número positivo");
        }
        return historialRepository.findById(id);
    }

    public HistorialMedicoModels save(HistorialMedicoModels hist) {
        if (hist == null) {
            throw new IllegalArgumentException("El objeto Historial no puede ser nulo");
        }

        // Validar que al menos un campo esté poblado
        if ((hist.getAntecedentesMedicos() == null || hist.getAntecedentesMedicos().trim().isEmpty()) &&
            (hist.getCirugiasPrevias() == null || hist.getCirugiasPrevias().trim().isEmpty()) &&
            (hist.getTratamientosMedicamentos() == null || hist.getTratamientosMedicamentos().trim().isEmpty()) &&
            (hist.getHabitos() == null || hist.getHabitos().trim().isEmpty()) &&
            (hist.getEnfermedadesFamiliares() == null || hist.getEnfermedadesFamiliares().trim().isEmpty()) &&
            (hist.getNotas() == null || hist.getNotas().trim().isEmpty())) {
            throw new IllegalArgumentException("El historial debe contener al menos un campo de información");
        }

        return historialRepository.save(hist);
    }

    public HistorialMedicoModels update(Long id, HistorialMedicoModels request) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del historial debe ser un número positivo");
        }
        if (request == null) {
            throw new IllegalArgumentException("El objeto Historial no puede ser nulo");
        }

        Optional<HistorialMedicoModels> opt = historialRepository.findById(id);
        if(opt.isPresent()){
            HistorialMedicoModels existing = opt.get();

            // Actualizar solo los campos que no son nulos
            if (request.getAntecedentesMedicos() != null) {
                existing.setAntecedentesMedicos(request.getAntecedentesMedicos());
            }
            if (request.getCirugiasPrevias() != null) {
                existing.setCirugiasPrevias(request.getCirugiasPrevias());
            }
            if (request.getTratamientosMedicamentos() != null) {
                existing.setTratamientosMedicamentos(request.getTratamientosMedicamentos());
            }
            if (request.getHabitos() != null) {
                existing.setHabitos(request.getHabitos());
            }
            if (request.getEnfermedadesFamiliares() != null) {
                existing.setEnfermedadesFamiliares(request.getEnfermedadesFamiliares());
            }
            if (request.getNotas() != null) {
                existing.setNotas(request.getNotas());
            }

            return historialRepository.save(existing);
        }
        throw new IllegalArgumentException("El historial con ID " + id + " no existe");
    }

    public boolean delete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del historial debe ser un número positivo");
        }
        if (!historialRepository.existsById(id)) {
            throw new IllegalArgumentException("El historial con ID " + id + " no existe");
        }
        try{
            historialRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
