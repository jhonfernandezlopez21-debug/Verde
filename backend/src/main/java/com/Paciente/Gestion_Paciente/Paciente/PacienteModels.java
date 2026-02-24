package com.Paciente.Gestion_Paciente.Paciente;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.Paciente.Gestion_Paciente.Factura.FacturaModels;
import com.Paciente.Gestion_Paciente.HistorialMedico.HistorialMedicoModels;
import com.Paciente.Gestion_Paciente.Seguro.SeguroModels;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
@Table(name= "Pacientes")

public class PacienteModels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPaciente;

    @Column
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String Nombre;

    @Column
    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
    private String Apellido;

    @Column
    @NotBlank(message = "El número de celular no puede estar vacío")
    @Pattern(regexp = "^[0-9]{7,15}$", message = "El número de celular debe contener entre 7 y 15 dígitos")
    private String NumeroDeCelular;

    @Column
    @NotBlank(message = "La edad no puede estar vacía")
    @Pattern(regexp = "^[0-9]{1,3}$", message = "La edad debe ser un número entre 1 y 150")
    private String Edad;

    @Column
    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(min = 2, max = 200, message = "La dirección debe tener entre 5 y 200 caracteres")
    private String Dirrecion;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "historial_id")
    @JsonManagedReference
    private HistorialMedicoModels HistorialMedico;

    @Column
    @NotBlank(message = "La fecha de ingreso no puede estar vacía")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha de ingreso debe estar en formato YYYY-MM-DD")
    private String FechaIngreso;

    @Column
    @NotBlank(message = "La cédula de identidad no puede estar vacía")
    @Pattern(regexp = "^[0-9]{6,20}$", message = "La cédula debe contener entre 6 y 20 caracteres numéricos")
    private String CedulaIdentidad;

    @Column
    @NotBlank(message = "La fecha de nacimiento no puede estar vacía")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha de nacimiento debe estar en formato YYYY-MM-DD")
    private String FechaNacimiento;

    @Column
    @NotBlank(message = "El sexo no puede estar vacío")
    @Pattern(regexp = "^(M|F|Masculino|Femenino|Otro)$", message = "El sexo debe ser Masculino, Femenino u Otro")
    private String Sexo;

    @Column
    @NotBlank(message = "La nacionalidad no puede estar vacía")
    @Size(min = 2, max = 50, message = "La nacionalidad debe tener entre 2 y 50 caracteres")
    private String Nacionalidad;

    @Column
    @NotBlank(message = "El estado civil no puede estar vacío")
    @Pattern(regexp = "^(Soltero|Casado|Divorciado|Viudo|Otro)$", message = "El estado civil debe ser válido")
    private String EstadoCivil;

    @Column
    @Size(max = 100, message = "La ocupación no debe exceder 100 caracteres")
    private String Ocupacion;

    @Column
    @NotBlank(message = "El nombre de la persona de contacto no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre de contacto debe tener entre 2 y 100 caracteres")
    private String PersonaContacto;

    @Column
    @NotBlank(message = "El teléfono de contacto no puede estar vacío")
    @Pattern(regexp = "^[0-9]{7,15}$", message = "El teléfono de contacto debe contener entre 7 y 15 dígitos")
    private String TelefonoContacto;

    @Column(columnDefinition = "TEXT")
    @Size(max = 500, message = "Las alergias conocidas no deben exceder 500 caracteres")
    private String AlergiasConocidas;

    @Column(columnDefinition = "TEXT")
    @Size(max = 500, message = "Los medicamentos actuales no deben exceder 500 caracteres")
    private String MedicamentosActuales;

    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "El motivo de ingreso no puede estar vacío")
    @Size(min = 5, max = 500, message = "El motivo de ingreso debe tener entre 5 y 500 caracteres")
    private String MotivoIngreso;

    @Column
    private Boolean hasHistorial = false;

    // nuevo: indica si el paciente tiene seguro
    @Column
    private Boolean TieneSeguro = false;

    // relación Many-to-One con Insurance (un paciente puede tener un seguro)
    @ManyToOne
    @JoinColumn(name = "insurance_id")
    private SeguroModels insurance;

    // Relación One-to-Many con Invoice (un paciente puede tener múltiples facturas)
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<FacturaModels> invoices;

    public long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getNumeroDeCelular() {
        return NumeroDeCelular;
    }

    public void setNumeroDeCelular(String numeroDeCelular) {
        NumeroDeCelular = numeroDeCelular;
    }

    public String getEdad() {
        return Edad;
    }

    public void setEdad(String edad) {
        Edad = edad;
    }

    public String getDirrecion() {
        return Dirrecion;
    }

    public void setDirrecion(String dirrecion) {
        Dirrecion = dirrecion;
    }

    public HistorialMedicoModels getHistorialMedico() {
        return HistorialMedico;
    }

    public void setHistorialMedico(HistorialMedicoModels historialMedico) {
        HistorialMedico = historialMedico;
    }

    public String getFechaIngreso() {
        return FechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        FechaIngreso = fechaIngreso;
    }

    public String getCedulaIdentidad() {
        return CedulaIdentidad;
    }

    public void setCedulaIdentidad(String cedulaIdentidad) {
        CedulaIdentidad = cedulaIdentidad;
    }

    public String getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        FechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String sexo) {
        Sexo = sexo;
    }

    public String getNacionalidad() {
        return Nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        Nacionalidad = nacionalidad;
    }

    public String getEstadoCivil() {
        return EstadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        EstadoCivil = estadoCivil;
    }

    public String getOcupacion() {
        return Ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        Ocupacion = ocupacion;
    }

    public String getPersonaContacto() {
        return PersonaContacto;
    }

    public void setPersonaContacto(String personaContacto) {
        PersonaContacto = personaContacto;
    }

    public String getTelefonoContacto() {
        return TelefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        TelefonoContacto = telefonoContacto;
    }

    public String getAlergiasConocidas() {
        return AlergiasConocidas;
    }

    public void setAlergiasConocidas(String alergiasConocidas) {
        AlergiasConocidas = alergiasConocidas;
    }

    public String getMedicamentosActuales() {
        return MedicamentosActuales;
    }

    public void setMedicamentosActuales(String medicamentosActuales) {
        MedicamentosActuales = medicamentosActuales;
    }

    public String getMotivoIngreso() {
        return MotivoIngreso;
    }

    public void setMotivoIngreso(String motivoIngreso) {
        MotivoIngreso = motivoIngreso;
    }

    public Boolean getHasHistorial() {
        return hasHistorial;
    }

    public void setHasHistorial(Boolean hasHistorial) {
        this.hasHistorial = hasHistorial;
    }

    public Boolean getTieneSeguro() {
        return TieneSeguro;
    }

    public void setTieneSeguro(Boolean tieneSeguro) {
        TieneSeguro = tieneSeguro;
    }

    public SeguroModels getInsurance() {
        return insurance;
    }

    public void setInsurance(SeguroModels insurance) {
        this.insurance = insurance;
    }

    public List<FacturaModels> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<FacturaModels> invoices) {
        this.invoices = invoices;
    }

}
