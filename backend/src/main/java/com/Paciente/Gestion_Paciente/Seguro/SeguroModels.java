package com.Paciente.Gestion_Paciente.Seguro;

import com.Paciente.Gestion_Paciente.Paciente.PacienteModels;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "Insurances")
public class SeguroModels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "El nombre de la compañía no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre de la compañía debe tener entre 2 y 100 caracteres")
    private String compania;

    @Column
    @NotBlank(message = "El número de póliza no puede estar vacío")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "El número de póliza solo puede contener letras mayúsculas y números")
    private String numeroPoliza;

    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "La cobertura no puede estar vacía")
    @Size(min = 10, max = 1000, message = "La cobertura debe tener entre 10 y 1000 caracteres")
    private String cobertura;

    @Column
    @NotBlank(message = "El deducible no puede estar vacío")
    @Pattern(regexp = "^\\d+(\\.\\d{1,2})?$", message = "El deducible debe ser un número válido")
    private String deducible;

    @Column
    @NotBlank(message = "El límite de cobertura no puede estar vacío")
    @Pattern(regexp = "^\\d+(\\.\\d{1,2})?$", message = "El límite de cobertura debe ser un número válido")
    private String limiteCobertura;

    @OneToMany(mappedBy = "insurance", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<PacienteModels> pacientes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompania() {
        return compania;
    }

    public void setCompania(String compania) {
        this.compania = compania;
    }

    public String getNumeroPoliza() {
        return numeroPoliza;
    }

    public void setNumeroPoliza(String numeroPoliza) {
        this.numeroPoliza = numeroPoliza;
    }

    public String getCobertura() {
        return cobertura;
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    public String getDeducible() {
        return deducible;
    }

    public void setDeducible(String deducible) {
        this.deducible = deducible;
    }

    public String getLimiteCobertura() {
        return limiteCobertura;
    }

    public void setLimiteCobertura(String limiteCobertura) {
        this.limiteCobertura = limiteCobertura;
    }

    public List<PacienteModels> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<PacienteModels> pacientes) {
        this.pacientes = pacientes;
    }
}
