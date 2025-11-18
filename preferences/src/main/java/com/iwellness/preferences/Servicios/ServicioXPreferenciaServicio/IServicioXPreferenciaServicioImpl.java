package com.iwellness.preferences.Servicios.ServicioXPreferenciaServicio;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwellness.preferences.Clientes.ServicioFeignClient;
import com.iwellness.preferences.DTO.PreferenciaDTO;
import com.iwellness.preferences.DTO.ServicioDTO;
import com.iwellness.preferences.DTO.ServicioXPreferenciaResponseDTO;
import com.iwellness.preferences.Entidades.Preferencias;
import com.iwellness.preferences.Entidades.ServicioXPreferencia;
import com.iwellness.preferences.Repositorios.IPreferenciasRepositorio;
import com.iwellness.preferences.Repositorios.IServicioXPreferenciaRepositorio;

import jakarta.transaction.Transactional;

@Service
public class IServicioXPreferenciaServicioImpl implements IServicioXPreferenciaServicio{

    @Autowired
    private IServicioXPreferenciaRepositorio servicioXPreferenciaRepositorio;

    @Autowired
    private IPreferenciasRepositorio preferenciasRepositorio;

    @Autowired
    private ServicioFeignClient servicioFeignClient;

    @Override
    public List<ServicioXPreferencia> buscarTodos() {
        return (List<ServicioXPreferencia>) servicioXPreferenciaRepositorio.findAll();
    }

    @Override
    public List<ServicioXPreferencia> obtenerPorIdServicio(Long idServicio) {
        List<ServicioXPreferencia> resultados = servicioXPreferenciaRepositorio.findByIdServicio(idServicio);
        if (resultados.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron relaciones con el Servicio ID: " + idServicio);
        }
        return resultados;
    }
    
    /**
     * Convierte una entidad ServicioXPreferencia a DTO de respuesta
     * Extrae explícitamente los IDs y nombres de la relación
     */
    public ServicioXPreferenciaResponseDTO convertirAResponseDTO(ServicioXPreferencia entidad) {
        ServicioXPreferenciaResponseDTO dto = new ServicioXPreferenciaResponseDTO();
        dto.setIdServicioXPreferencia(entidad.get_idServicioXPreferencia());
        dto.setIdServicio(entidad.getIdServicio());
        
        // Extraer información de la preferencia si existe y crear PreferenciaDTO anidado
        if (entidad.getPreferencia() != null) {
            dto.setIdPreferencia(entidad.getPreferencia().get_idPreferencias());
            
            // Crear PreferenciaDTO anidado
            PreferenciaDTO preferenciaDTO = new PreferenciaDTO();
            preferenciaDTO.setId(entidad.getPreferencia().get_idPreferencias());
            preferenciaDTO.setNombre(entidad.getPreferencia().getNombre());
            preferenciaDTO.setImagen(entidad.getPreferencia().getImagen());
            
            dto.setNombrePreferencia(preferenciaDTO);
        }
        
        return dto;
    }
    
    /**
     * Obtiene las preferencias de un servicio como DTOs de respuesta
     */
    public List<ServicioXPreferenciaResponseDTO> obtenerPorIdServicioDTO(Long idServicio) {
        List<ServicioXPreferencia> resultados = servicioXPreferenciaRepositorio.findByIdServicio(idServicio);
        if (resultados.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron relaciones con el Servicio ID: " + idServicio);
        }
        
        return resultados.stream()
            .map(this::convertirAResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<ServicioXPreferencia> findByPreferencia_IdPreferencias (Long idPreferencia){
        List<ServicioXPreferencia> resultados = servicioXPreferenciaRepositorio.findByPreferencia_IdPreferencias(idPreferencia);
    if (resultados.isEmpty()) {
        throw new IllegalArgumentException("No se encontraron relaciones con la Preferencia ID: " + idPreferencia);
    }
    return resultados;
    }

    @Override
    public ServicioXPreferencia guardar(ServicioXPreferencia servicioXPreferencia) {
        // Validar que la preferencia no sea nula
        if (servicioXPreferencia.getPreferencia() == null || 
            servicioXPreferencia.getPreferencia().get_idPreferencias() == null) {
            throw new IllegalArgumentException("Debe especificar un idPreferencias válido.");
        }

        Preferencias preferenciaExistente = preferenciasRepositorio
            .findById(servicioXPreferencia.getPreferencia().get_idPreferencias())
            .orElseThrow(() -> new IllegalArgumentException("La preferencia no existe."));

        ServicioDTO servicioDTO;
        try {
            servicioDTO = servicioFeignClient.obtenerServicio(servicioXPreferencia.getIdServicio());
        } catch (Exception e) {
            throw new RuntimeException("Error al comunicarse con el microservicio de servicios: " + e.getMessage());
        }

        if (servicioDTO == null) {
            throw new IllegalArgumentException("El servicio con ID " + servicioXPreferencia.getIdServicio() + " no existe.");
        }

        servicioXPreferencia.setPreferencia(preferenciaExistente);
        
        return servicioXPreferenciaRepositorio.save(servicioXPreferencia);
    }

    public boolean existePorId(Long id) {
        return servicioXPreferenciaRepositorio.existsById(id);
    }

    @Override
    public void eliminar(Long id) {
        if (!existePorId(id)) {
            throw new IllegalArgumentException("No se encontró la relación con ID: " + id);
        }

        servicioXPreferenciaRepositorio.deleteById(id);    
    }

    @Override
    public ServicioXPreferencia actualizar(ServicioXPreferencia servicioXPreferencia){
        if (!existePorId(servicioXPreferencia.get_idServicioXPreferencia())) {
            throw new IllegalArgumentException("No se encontró la relación con ID: " + servicioXPreferencia.get_idServicioXPreferencia());
        }
        return servicioXPreferenciaRepositorio.save(servicioXPreferencia);
    }

    @Transactional
    public void eliminarPreferenciasPorServicio(Long idServicio) {
        servicioXPreferenciaRepositorio.deleteByidServicio(idServicio);
    }

}
