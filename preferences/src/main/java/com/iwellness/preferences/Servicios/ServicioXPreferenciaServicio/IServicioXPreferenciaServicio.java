package com.iwellness.preferences.Servicios.ServicioXPreferenciaServicio;

import java.util.List;

import com.iwellness.preferences.DTO.ServicioXPreferenciaResponseDTO;
import com.iwellness.preferences.Entidades.ServicioXPreferencia;

public interface IServicioXPreferenciaServicio {

    List<ServicioXPreferencia> buscarTodos();

    List<ServicioXPreferencia> obtenerPorIdServicio(Long idServicio);
    
    List<ServicioXPreferenciaResponseDTO> obtenerPorIdServicioDTO(Long idServicio);

    List<ServicioXPreferencia> findByPreferencia_IdPreferencias (Long idPreferencia);

    ServicioXPreferencia guardar(ServicioXPreferencia servicioXPreferencia);

    void eliminar(Long id);

    ServicioXPreferencia actualizar(ServicioXPreferencia servicioXPreferencia);

    void eliminarPreferenciasPorServicio(Long idServicio);

}
