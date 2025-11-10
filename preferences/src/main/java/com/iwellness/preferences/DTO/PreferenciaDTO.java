package com.iwellness.preferences.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreferenciaDTO {
    @JsonProperty("id_preferencia")
    private Long id;
    @JsonProperty("nombre")
    private String nombre;
    @JsonProperty("imagen")
    private String imagen;
}
