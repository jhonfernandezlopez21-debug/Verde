package com.Paciente.Gestion_Paciente.HistorialMedico;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Historial_Medico")
public class HistorialMedicoModels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    @Size(max = 2000, message = "Los antecedentes médicos no pueden exceder 2000 caracteres")
    private String antecedentesMedicos;

    @Column(columnDefinition = "TEXT")
    @Size(max = 2000, message = "Las cirugías previas no pueden exceder 2000 caracteres")
    private String cirugiasPrevias;

    @Column(columnDefinition = "TEXT")
    @Size(max = 2000, message = "Los tratamientos no pueden exceder 2000 caracteres")
    private String tratamientosMedicamentos;

    @Column
    @Size(max = 500, message = "Los hábitos no pueden exceder 500 caracteres")
    private String habitos;

    @Column(columnDefinition = "TEXT")
    @Size(max = 2000, message = "Las enfermedades familiares no pueden exceder 2000 caracteres")
    private String enfermedadesFamiliares;

    @Column(columnDefinition = "TEXT")
    @Size(max = 2000, message = "Las notas no pueden exceder 2000 caracteres")
    private String notas;

    @OneToOne(mappedBy = "HistorialMedico")
    @JsonBackReference
    private com.Paciente.Gestion_Paciente.Paciente.PacienteModels paciente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAntecedentesMedicos() {
        return antecedentesMedicos;
    }

    public void setAntecedentesMedicos(String antecedentesMedicos) {
        this.antecedentesMedicos = antecedentesMedicos;
    }

    public String getCirugiasPrevias() {
        return cirugiasPrevias;
    }

    public void setCirugiasPrevias(String cirugiasPrevias) {
        this.cirugiasPrevias = cirugiasPrevias;
    }

    public String getTratamientosMedicamentos() {
        return tratamientosMedicamentos;
    }

    public void setTratamientosMedicamentos(String tratamientosMedicamentos) {
        this.tratamientosMedicamentos = tratamientosMedicamentos;
    }

    public String getHabitos() {
        return habitos;
    }

    public void setHabitos(String habitos) {
        this.habitos = habitos;
    }

    public String getEnfermedadesFamiliares() {
        return enfermedadesFamiliares;
    }

    public void setEnfermedadesFamiliares(String enfermedadesFamiliares) {
        this.enfermedadesFamiliares = enfermedadesFamiliares;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public com.Paciente.Gestion_Paciente.Paciente.PacienteModels getPaciente() {
        return paciente;
    }

    public void setPaciente(com.Paciente.Gestion_Paciente.Paciente.PacienteModels paciente) {
        this.paciente = paciente;
    }
}
