package com.Paciente.Gestion_Paciente.Paciente;

import com.Paciente.Gestion_Paciente.Seguro.SeguroModels;
import com.Paciente.Gestion_Paciente.HistorialMedico.HistorialMedicoModels;
import com.Paciente.Gestion_Paciente.HistorialMedico.IHistorialRepository;
import com.Paciente.Gestion_Paciente.Seguro.ISeguroRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class PacienteServirces implements IPacienteService {
    @Autowired
    IPacienteRepositories pacienteRepositories;

    @Autowired
    ISeguroRepository insuranceRepository;

    @Autowired
    IHistorialRepository historialRepository;

    public ArrayList<PacienteModels> getPaciente(){
        return (ArrayList<PacienteModels>) pacienteRepositories.findAll();
    }

    public PacienteModels savePaciente(PacienteModels Paciente){
        // Manejar seguro: si viene con id, buscar; si viene sin id, crear el seguro primero
        if(Paciente.getInsurance() != null){
            SeguroModels ins = Paciente.getInsurance();
            if(ins.getId() != null){
                Optional<SeguroModels> insOpt = insuranceRepository.findById(ins.getId());
                insOpt.ifPresent(Paciente::setInsurance);
                Paciente.setTieneSeguro(true);
            } else {
                // guardar nuevo seguro y asignarlo
                SeguroModels savedIns = insuranceRepository.save(ins);
                Paciente.setInsurance(savedIns);
                Paciente.setTieneSeguro(true);
            }
        } else {
            // No viene seguro, asegurar flag false
            Paciente.setTieneSeguro(false);
            Paciente.setInsurance(null);
        }
        // Manejar historial medico: si viene con id, intentar recuperar la entidad existente
        if(Paciente.getHistorialMedico() != null){
            HistorialMedicoModels hist = Paciente.getHistorialMedico();
            if(hist.getId() != null){
                historialRepository.findById(hist.getId()).ifPresent(Paciente::setHistorialMedico);
            } else {
                // No tiene id: será persistido por cascade cuando guardemos el paciente
            }
        }

        return pacienteRepositories.save(Paciente);
    }

    public Optional<PacienteModels> getByid(Long id){
        return  pacienteRepositories.findById(id);
    }

    public PacienteModels updateByID(PacienteModels request, Long id){
        PacienteModels paciente = pacienteRepositories.findById(id).get();

        paciente.setNombre(request.getNombre());
        paciente.setApellido(request.getApellido());
        paciente.setNumeroDeCelular(request.getNumeroDeCelular());
        paciente.setEdad(request.getEdad());
        // nuevos campos del formulario
        paciente.setCedulaIdentidad(request.getCedulaIdentidad());
        paciente.setFechaNacimiento(request.getFechaNacimiento());
        paciente.setSexo(request.getSexo());
        paciente.setNacionalidad(request.getNacionalidad());
        paciente.setEstadoCivil(request.getEstadoCivil());
        paciente.setOcupacion(request.getOcupacion());
        paciente.setPersonaContacto(request.getPersonaContacto());
        paciente.setTelefonoContacto(request.getTelefonoContacto());
        paciente.setAlergiasConocidas(request.getAlergiasConocidas());
        paciente.setMedicamentosActuales(request.getMedicamentosActuales());
        paciente.setMotivoIngreso(request.getMotivoIngreso());
        paciente.setHasHistorial(request.getHasHistorial());
        paciente.setDirrecion(request.getDirrecion());
        // actualizar historial medico (si se envía uno)
        if(request.getHistorialMedico() != null){
            HistorialMedicoModels incoming = request.getHistorialMedico();
            if(incoming.getId() != null){
                historialRepository.findById(incoming.getId()).ifPresent(paciente::setHistorialMedico);
            } else {
                // asignar nuevo historial (cascade permitirá persistirlo)
                paciente.setHistorialMedico(incoming);
            }
        } else {
            paciente.setHistorialMedico(null);
        }
        paciente.setFechaIngreso(request.getFechaIngreso());
        // nuevos campos
        paciente.setTieneSeguro(request.getTieneSeguro());
        if(request.getInsurance() != null){
            SeguroModels ins = request.getInsurance();
            if(ins.getId() != null){
                Optional<SeguroModels> insOpt = insuranceRepository.findById(ins.getId());
                insOpt.ifPresent(paciente::setInsurance);
            } else {
                // crear nuevo seguro y asignar
                SeguroModels savedIns = insuranceRepository.save(ins);
                paciente.setInsurance(savedIns);
            }
        } else {
            paciente.setInsurance(null);
        }
        // persist the changes
        return pacienteRepositories.save(paciente);
    }

    public Boolean deletePaciente (Long id){
        try{
            pacienteRepositories.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
