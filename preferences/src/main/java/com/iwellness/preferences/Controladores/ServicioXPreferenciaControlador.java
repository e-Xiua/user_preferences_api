package com.iwellness.preferences.Controladores;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iwellness.preferences.Entidades.ServicioXPreferencia;
import com.iwellness.preferences.Servicios.ServicioXPreferenciaServicio.IServicioXPreferenciaServicio;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/servicioXPreferencia")
public class ServicioXPreferenciaControlador {

    @Autowired
    private IServicioXPreferenciaServicio servicioXPreferenciaServicio;

    @GetMapping("/all")
    public ResponseEntity<?> obtenerTodas() {
        return ResponseEntity.ok(servicioXPreferenciaServicio.buscarTodos());
    }
    
    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody ServicioXPreferencia nuevaRelacion) {
        try {
            return ResponseEntity.ok(servicioXPreferenciaServicio.guardar(nuevaRelacion));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al guardar el servicioXpreferencia: " + e.getMessage());
        } 
    }

    @PutMapping("/editar")
    public ResponseEntity<?> actualizarPreferencia(@RequestBody ServicioXPreferencia servicioXPreferencia) {
        
        try {
            return ResponseEntity.ok(servicioXPreferenciaServicio.actualizar(servicioXPreferencia));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar el servicioXPreferencia: " + e.getMessage());
        }
    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            servicioXPreferenciaServicio.eliminar(id);
            return ResponseEntity.ok("servicioXpreferencia eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el servicioXpreferencia con ID: " + id);
        }    
    }
    
    @GetMapping("/servicio/{idServicio}")
    public ResponseEntity<?> obtenerPorServicio(@PathVariable Long idServicio) {
        try{
            // Retorna DTOs con PreferenciaDTO anidado
            return ResponseEntity.ok(servicioXPreferenciaServicio.obtenerPorIdServicioDTO(idServicio));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningun servicio con ID: " + idServicio);
        }
    }

    @GetMapping("/preferencia/{idPreferencia}")
    public ResponseEntity<?> obtenerPorPreferencia(@PathVariable Long idPreferencia) {
        try{
            return ResponseEntity.ok(servicioXPreferenciaServicio.findByPreferencia_IdPreferencias (idPreferencia));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ninguna preferencia con ID: " + idPreferencia);
        }
    }

    @DeleteMapping("/eliminarPorServicio/{idServicio}")
    public ResponseEntity<String> eliminarPreferenciasPorServicio(@PathVariable Long idServicio) {
        servicioXPreferenciaServicio.eliminarPreferenciasPorServicio(idServicio);
        return ResponseEntity.ok("Preferencias del servicio eliminadas correctamente");
    }
}
