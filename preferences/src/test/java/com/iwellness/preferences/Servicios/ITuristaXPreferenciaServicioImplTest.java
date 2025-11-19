package com.iwellness.preferences.Servicios;


import static org.junit.jupiter.api.Assertions.*;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iwellness.preferences.Clientes.TuristaFeignClient;
import com.iwellness.preferences.DTO.TuristaDTO;
import com.iwellness.preferences.Entidades.Preferencias;
import com.iwellness.preferences.Entidades.TuristaXPreferencia;
import com.iwellness.preferences.Repositorios.IPreferenciasRepositorio;
import com.iwellness.preferences.Repositorios.ITuristaXPreferenciaRepositorio;
import com.iwellness.preferences.Servicios.TuristaXPreferenciaServicio.ITuristaXPreferenciaServicioImpl;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas de ITuristaXPreferenciaServicioImpl")
class ITuristaXPreferenciaServicioImplTest {

    @Mock
    private ITuristaXPreferenciaRepositorio turistaXPreferenciaRepositorio;

    @Mock
    private IPreferenciasRepositorio preferenciasRepositorio;

    @Mock
    private TuristaFeignClient turistaFeignClient;

    @InjectMocks
    private ITuristaXPreferenciaServicioImpl servicio;

    private Preferencias preferencia;
    private TuristaXPreferencia turistaXPreferencia;
    private TuristaDTO turistaDTO;

    @BeforeEach
    void setUp() {
        preferencia = new Preferencias();
        preferencia.set_idPreferencias(1L);
        preferencia.setNombre("Cultura");

        turistaXPreferencia = new TuristaXPreferencia();
        turistaXPreferencia.set_idTuristaXPreferencia(1L);
        turistaXPreferencia.setIdUsuario(10L);
        turistaXPreferencia.setPreferencia(preferencia);

        turistaDTO = new TuristaDTO();
    }

    @Test
    @DisplayName("Guardar - Debería guardar relación turista-preferencia válida")
    void testGuardar_TuristaXPreferenciaValida_DevuelveGuardado() {
        // Arrange
        when(preferenciasRepositorio.findById(1L)).thenReturn(Optional.of(preferencia));
        when(turistaFeignClient.obtenerTurista(10L)).thenReturn(turistaDTO);
        when(turistaXPreferenciaRepositorio.save(any())).thenReturn(turistaXPreferencia);

        // Act
        TuristaXPreferencia resultado = servicio.guardar(turistaXPreferencia);

        // Assert
        assertNotNull(resultado);
        assertEquals(10L, resultado.getIdUsuario());
        verify(turistaXPreferenciaRepositorio, times(1)).save(any());
    }

    @Test
    @DisplayName("Guardar - Debería lanzar excepción si preferencia no existe")
    void testGuardar_PreferenciaNoExiste_LanzaExcepcion() {
        // Arrange
        when(preferenciasRepositorio.findById(1L)).thenReturn(Optional.empty());

        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            servicio.guardar(turistaXPreferencia);
        });
    }

    @Test
    @DisplayName("Guardar - Debería lanzar excepción si turista no existe")
    void testGuardar_TuristaNoExiste_LanzaExcepcion() {
        // Arrange
        when(preferenciasRepositorio.findById(1L)).thenReturn(Optional.of(preferencia));
        when(turistaFeignClient.obtenerTurista(10L)).thenReturn(null);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            servicio.guardar(turistaXPreferencia);
        });
    }

    @Test
    @DisplayName("Guardar - Debería lanzar RuntimeException si error en FeignClient")
    void testGuardar_ErrorFeignClient_LanzaRuntimeException() {
        // Arrange
        when(preferenciasRepositorio.findById(1L)).thenReturn(Optional.of(preferencia));
        when(turistaFeignClient.obtenerTurista(10L)).thenThrow(new RuntimeException("Feign error"));

        // Assert
        assertThrows(RuntimeException.class, () -> {
            servicio.guardar(turistaXPreferencia);
        });
    }

    @Test
    @DisplayName("Guardar - Debería lanzar excepción si preferencia es nula")
    void testGuardar_PreferenciaNula() {
        // Arrange
        TuristaXPreferencia txp = new TuristaXPreferencia();
        txp.setIdUsuario(10L);
        txp.setPreferencia(null);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            servicio.guardar(txp);
        });
    }

    @Test
    @DisplayName("BuscarTodos - Debería retornar lista de relaciones")
    void testBuscarTodos() {
        // Arrange
        List<TuristaXPreferencia> lista = Arrays.asList(turistaXPreferencia);
        when(turistaXPreferenciaRepositorio.findAll()).thenReturn(lista);

        // Act
        List<TuristaXPreferencia> resultado = servicio.buscarTodos();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(turistaXPreferenciaRepositorio).findAll();
    }

    @Test
    @DisplayName("ObtenerPorIdUsuario - Debería retornar relaciones del usuario")
    void testObtenerPorIdUsuario() {
        // Arrange
        List<TuristaXPreferencia> lista = Arrays.asList(turistaXPreferencia);
        when(turistaXPreferenciaRepositorio.findByIdUsuario(10L)).thenReturn(lista);

        // Act
        List<TuristaXPreferencia> resultado = servicio.obtenerPorIdUsuario(10L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(turistaXPreferenciaRepositorio).findByIdUsuario(10L);
    }

    @Test
    @DisplayName("FindByPreferenciaId - Debería retornar relaciones de la preferencia")
    void testFindByPreferenciaId_Success() {
        // Arrange
        List<TuristaXPreferencia> lista = Arrays.asList(turistaXPreferencia);
        when(turistaXPreferenciaRepositorio.findByPreferencia_IdPreferencias(1L)).thenReturn(lista);

        // Act
        List<TuristaXPreferencia> resultado = servicio.findByPreferencia_IdPreferencias(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("FindByPreferenciaId - Debería lanzar excepción si no hay relaciones")
    void testFindByPreferenciaId_Empty() {
        // Arrange
        when(turistaXPreferenciaRepositorio.findByPreferencia_IdPreferencias(99L))
            .thenReturn(Collections.emptyList());

        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            servicio.findByPreferencia_IdPreferencias(99L);
        });
    }

    @Test
    @DisplayName("Actualizar - Debería actualizar relación existente")
    void testActualizar_Success() {
        // Arrange
        when(turistaXPreferenciaRepositorio.existsById(1L)).thenReturn(true);
        when(turistaXPreferenciaRepositorio.save(any())).thenReturn(turistaXPreferencia);

        // Act
        TuristaXPreferencia resultado = servicio.actualizar(turistaXPreferencia);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.get_idTuristaXPreferencia());
        verify(turistaXPreferenciaRepositorio).save(any());
    }

    @Test
    @DisplayName("Actualizar - Debería lanzar excepción si relación no existe")
    void testActualizar_NotFound() {
        // Arrange
        when(turistaXPreferenciaRepositorio.existsById(1L)).thenReturn(false);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            servicio.actualizar(turistaXPreferencia);
        });
    }

    @Test
    @DisplayName("Eliminar - Debería eliminar relación existente")
    void testEliminar_Success() {
        // Arrange
        when(turistaXPreferenciaRepositorio.existsById(1L)).thenReturn(true);

        // Act
        servicio.eliminar(1L);

        // Assert
        verify(turistaXPreferenciaRepositorio).deleteById(1L);
    }

    @Test
    @DisplayName("Eliminar - Debería lanzar excepción si relación no existe")
    void testEliminar_NotFound() {
        // Arrange
        when(turistaXPreferenciaRepositorio.existsById(1L)).thenReturn(false);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            servicio.eliminar(1L);
        });
    }

    @Test
    @DisplayName("EliminarPreferenciasPorTurista - Debería eliminar por idUsuario")
    void testEliminarPreferenciasPorTurista() {
        // Act
        servicio.eliminarPreferenciasPorTurista(10L);

        // Assert
        verify(turistaXPreferenciaRepositorio).deleteByidUsuario(10L);
    }

    @Test
    @DisplayName("ExistePorId - Debería retornar true si existe")
    void testExistePorId_True() {
        // Arrange
        when(turistaXPreferenciaRepositorio.existsById(1L)).thenReturn(true);

        // Act
        boolean resultado = servicio.existePorId(1L);

        // Assert
        assertTrue(resultado);
    }

    @Test
    @DisplayName("ExistePorId - Debería retornar false si no existe")
    void testExistePorId_False() {
        // Arrange
        when(turistaXPreferenciaRepositorio.existsById(99L)).thenReturn(false);

        // Act
        boolean resultado = servicio.existePorId(99L);

        // Assert
        assertFalse(resultado);
    }
}