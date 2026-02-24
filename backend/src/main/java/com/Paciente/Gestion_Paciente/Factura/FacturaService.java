package com.Paciente.Gestion_Paciente.Factura;

import com.Paciente.Gestion_Paciente.Paciente.PacienteModels;
import com.Paciente.Gestion_Paciente.Paciente.IPacienteRepositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

@Service
public class FacturaService implements IFacturaService {

    @Autowired
    private IFacturaRepository FacturaRepository;

    @Autowired
    private IPacienteRepositories pacienteRepository;

    public List<FacturaModels> getAll(){
        return FacturaRepository.findAll();
    }

    public Optional<FacturaModels> getById(Long id){
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID de la factura debe ser un número positivo");
        }
        return FacturaRepository.findById(id);
    }

    public FacturaModels save(FacturaModels invoice){
        if (invoice == null) {
            throw new IllegalArgumentException("El objeto Factura no puede ser nulo");
        }

        // Validar descripción
        if (invoice.getDescripcion() == null || invoice.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción de la factura es obligatoria");
        }

        // Validar subtotal
        if (invoice.getSubtotal() == null) {
            throw new IllegalArgumentException("El subtotal es obligatorio");
        }
        if (invoice.getSubtotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El subtotal debe ser mayor a 0");
        }



        // Validar paciente
        PacienteModels p = invoice.getPaciente();
        if (p == null || p.getIdPaciente() <= 0) {
            throw new IllegalArgumentException("La factura debe estar asociada a un paciente válido");
        }

        // Validar que el paciente existe
        Optional<PacienteModels> existing = pacienteRepository.findById(p.getIdPaciente());
        if (!existing.isPresent()) {
            throw new IllegalArgumentException("El paciente con ID " + p.getIdPaciente() + " no existe");
        }

        p = existing.get();
        invoice.setPaciente(p);

        // calcular total: subtotal - cobertura del seguro (si existe en el paciente ya resuelto)
        BigDecimal subtotal = invoice.getSubtotal();
        BigDecimal descuento = BigDecimal.ZERO;
        if(p != null && p.getInsurance() != null){
            try{
                String coberturaStr = p.getInsurance().getCobertura();
                String limiteStr = p.getInsurance().getLimiteCobertura();

                // Priorizar límite de cobertura numérico si existe
                if(limiteStr != null){
                    String num = limiteStr.replaceAll("[^0-9.,]", "").replace(',', '.');
                    if(!num.isEmpty()){
                        try{
                            descuento = new BigDecimal(num);
                        }catch(Exception ex){
                            descuento = BigDecimal.ZERO;
                        }
                    }
                }

                // Si no se obtuvo un número desde limiteCobertura, intentar interpretar 'cobertura'
                if(descuento.compareTo(BigDecimal.ZERO) == 0 && coberturaStr != null){
                    String t = coberturaStr.trim().toLowerCase();
                    if(t.equals("total") || t.equals("100%")){
                        descuento = subtotal; // cubre todo
                    } else if(t.endsWith("%")){
                        // porcentaje: 20% -> 0.2 * subtotal
                        String pct = t.replaceAll("[^0-9.,]", "").replace(',', '.');
                        if(!pct.isEmpty()){
                            try{
                                BigDecimal percent = new BigDecimal(pct);
                                BigDecimal factor = percent.divide(new BigDecimal("100"));
                                descuento = subtotal.multiply(factor);
                            }catch(Exception ex){
                                descuento = BigDecimal.ZERO;
                            }
                        }
                    } else {
                        // intentar parsear número directo
                        String num = t.replaceAll("[^0-9.,]", "").replace(',', '.');
                        if(!num.isEmpty()){
                            try{
                                descuento = new BigDecimal(num);
                            }catch(Exception ex){
                                descuento = BigDecimal.ZERO;
                            }
                        }
                    }
                }

            }catch(Exception ex){
                descuento = BigDecimal.ZERO;
            }
        }
        BigDecimal total = subtotal.subtract(descuento);
        if(total.compareTo(BigDecimal.ZERO) < 0) total = BigDecimal.ZERO;
        invoice.setTotal(total);

        FacturaModels saved = FacturaRepository.save(invoice);
        return saved;
    }

    public void delete(Long id){
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID de la factura debe ser un número positivo");
        }
        if (!FacturaRepository.existsById(id)) {
            throw new IllegalArgumentException("La factura con ID " + id + " no existe");
        }
        FacturaRepository.deleteById(id);
    }
}
