package com.iwellness.preferences.Entidades;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "servicio_x_preferencia")
@AllArgsConstructor
@NoArgsConstructor
public class ServicioXPreferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio_x_preferencia")
    @JsonProperty("idServicioXPreferencia")
    private Long _idServicioXPreferencia;

    @Column(name = "id_servicio", nullable = false)
    @JsonProperty("idServicio")
    private Long idServicio;  // Se relaciona con el microservicio de servicios

    @ManyToOne
    @JoinColumn(name = "id_preferencia", referencedColumnName = "id_preferencia", nullable = false)
    private Preferencias preferencia;
    
    // Campos transitorios para JSON - no se persisten en BD
    @Transient
    @JsonProperty("idPreferencia")
    public Long getIdPreferencia() {
        return preferencia != null ? preferencia.get_idPreferencias() : null;
    }
    
    @Transient
    @JsonProperty("nombrePreferencia")
    public String getNombrePreferencia() {
        return preferencia != null ? preferencia.getNombre() : null;
    }
    
    @Transient
    @JsonProperty("imagenPreferencia")
    public String getImagenPreferencia() {
        return preferencia != null ? preferencia.getImagen() : null;
    }
}
