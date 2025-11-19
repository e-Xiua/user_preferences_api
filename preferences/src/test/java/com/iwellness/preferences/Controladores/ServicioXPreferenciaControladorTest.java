package com.iwellness.preferences.Controladores;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwellness.preferences.Entidades.ServicioXPreferencia;
import com.iwellness.preferences.Servicios.ServicioXPreferenciaServicio.IServicioXPreferenciaServicio;

public class ServicioXPreferenciaControladorTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IServicioXPreferenciaServicio servicio;

    @InjectMocks
    private ServicioXPreferenciaControlador servicioXPreferenciaControlador;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(servicioXPreferenciaControlador).build();
    }

    @Test
public void testObtenerTodas() throws Exception {
    ServicioXPreferencia s1 = new ServicioXPreferencia();
    ServicioXPreferencia s2 = new ServicioXPreferencia();
    List<ServicioXPreferencia> lista = Arrays.asList(s1, s2);

    when(servicio.buscarTodos()).thenReturn(lista);

    mockMvc.perform(get("/api/servicioXPreferencia/all"))
            .andExpect(status().isOk());
}

@Test
public void testCrear() throws Exception {
    ServicioXPreferencia nuevo = new ServicioXPreferencia();
    when(servicio.guardar(any(ServicioXPreferencia.class))).thenReturn(nuevo);

    mockMvc.perform(post("/api/servicioXPreferencia/crear")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(nuevo)))
            .andExpect(status().isOk());
}

@Test
public void testCrearError() throws Exception {
    ServicioXPreferencia nuevo = new ServicioXPreferencia();
    when(servicio.guardar(any(ServicioXPreferencia.class))).thenThrow(new RuntimeException("Error"));

    mockMvc.perform(post("/api/servicioXPreferencia/crear")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(nuevo)))
            .andExpect(status().isBadRequest());
}

@Test
public void testActualizarPreferencia() throws Exception {
    ServicioXPreferencia existente = new ServicioXPreferencia();
    when(servicio.actualizar(any(ServicioXPreferencia.class))).thenReturn(existente);

    mockMvc.perform(put("/api/servicioXPreferencia/editar")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(existente)))
            .andExpect(status().isOk());
}

@Test
public void testActualizarPreferenciaError() throws Exception {
    ServicioXPreferencia existente = new ServicioXPreferencia();
    when(servicio.actualizar(any(ServicioXPreferencia.class))).thenThrow(new RuntimeException("Error"));

    mockMvc.perform(put("/api/servicioXPreferencia/editar")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(existente)))
            .andExpect(status().isBadRequest());
}

@Test
public void testEliminar() throws Exception {
    mockMvc.perform(delete("/api/servicioXPreferencia/eliminar/1"))
            .andExpect(status().isOk());

    verify(servicio).eliminar(1L);
}

@Test
public void testEliminarError() throws Exception {
    doThrow(new NoSuchElementException("No encontrado")).when(servicio).eliminar(1L);

    mockMvc.perform(delete("/api/servicioXPreferencia/eliminar/1"))
            .andExpect(status().isNotFound());
}

@Test
public void testObtenerPorServicio() throws Exception {
    List<ServicioXPreferencia> lista = Arrays.asList(new ServicioXPreferencia());
    when(servicio.obtenerPorIdServicio(1L)).thenReturn(lista);

    mockMvc.perform(get("/api/servicioXPreferencia/servicio/1"))
            .andExpect(status().isOk());
}

@Test
public void testObtenerPorServicioError() throws Exception {
    when(servicio.obtenerPorIdServicioDTO(1L)).thenThrow(new RuntimeException("No encontrado"));

    mockMvc.perform(get("/api/servicioXPreferencia/servicio/1"))
            .andExpect(status().isNotFound());
}

@Test
public void testObtenerPorPreferencia() throws Exception {
    List<ServicioXPreferencia> lista = Arrays.asList(new ServicioXPreferencia());
    when(servicio.findByPreferencia_IdPreferencias(1L)).thenReturn(lista);

    mockMvc.perform(get("/api/servicioXPreferencia/preferencia/1"))
            .andExpect(status().isOk());
}

@Test
public void testObtenerPorPreferenciaError() throws Exception {
    when(servicio.findByPreferencia_IdPreferencias(1L)).thenThrow(new RuntimeException("No encontrado"));

    mockMvc.perform(get("/api/servicioXPreferencia/preferencia/1"))
            .andExpect(status().isNotFound());
}

@Test
public void testEliminarPreferenciasPorServicio() throws Exception {
    mockMvc.perform(delete("/api/servicioXPreferencia/eliminarPorServicio/1"))
            .andExpect(status().isOk());

    verify(servicio).eliminarPreferenciasPorServicio(1L);
}


}
