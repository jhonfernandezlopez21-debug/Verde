package com.Paciente.Gestion_Paciente.Factura;

import com.Paciente.Gestion_Paciente.Paciente.PacienteModels;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Invoices")
public class FacturaModels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(min = 10, max = 1000, message = "La descripción debe tener entre 10 y 1000 caracteres")
    private String descripcion;

    @Column(precision = 12, scale = 2)
    @NotNull(message = "El subtotal no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El subtotal debe ser mayor a 0")
    @Digits(integer = 10, fraction = 2, message = "El subtotal debe tener máximo 2 decimales")
    private BigDecimal subtotal;

    @Column(precision = 12, scale = 2)

    private BigDecimal total;


    // relación Many-to-One con Paciente (una factura pertenece a un paciente)
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    @NotNull(message = "La factura debe estar asociada a un paciente")
    private PacienteModels paciente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public PacienteModels getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteModels paciente) {
        this.paciente = paciente;
    }



}
