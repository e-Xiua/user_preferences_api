package com.iwellness.preferences.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para respuestas de ServicioXPreferencia
 * Expone explícitamente los IDs en lugar de objetos anidados
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicioXPreferenciaResponseDTO {
    
    @JsonProperty("idServicioXPreferencia")
    private Long idServicioXPreferencia;
    
    @JsonProperty("idServicio")
    private Long idServicio;
    
    @JsonProperty("idPreferencia")
    private Long idPreferencia;
    
    @JsonProperty("nombrePreferencia")
    private PreferenciaDTO nombrePreferencia;
}
