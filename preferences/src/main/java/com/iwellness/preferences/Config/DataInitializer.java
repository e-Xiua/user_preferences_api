package com.iwellness.preferences.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.iwellness.preferences.Entidades.Preferencias;
import com.iwellness.preferences.Repositorios.IPreferenciasRepositorio;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private IPreferenciasRepositorio preferenciasRepositorio;

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya existen preferencias
        if (preferenciasRepositorio.count() == 0) {
            log.info("Inicializando preferencias por defecto...");
            
            // Crear preferencias iniciales para turismo de bienestar
            crearPreferencia("Yoga", "https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?w=400");
            crearPreferencia("Meditación", "https://images.unsplash.com/photo-1506126613408-eca07ce68773?w=400");
            crearPreferencia("Spa", "https://images.unsplash.com/photo-1540555700478-4be289fbecef?w=400");
            crearPreferencia("Masajes", "https://images.unsplash.com/photo-1544161515-4ab6ce6db874?w=400");
            crearPreferencia("Senderismo", "https://images.unsplash.com/photo-1551632811-561732d1e306?w=400");
            crearPreferencia("Aguas Termales", "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=400");
            crearPreferencia("Nutrición Saludable", "https://images.unsplash.com/photo-1490645935967-10de6ba17061?w=400");
            crearPreferencia("Fitness", "https://images.unsplash.com/photo-1534438327276-14e5300c3a48?w=400");
            crearPreferencia("Mindfulness", "https://images.unsplash.com/photo-1499209974431-9dddcece7f88?w=400");
            crearPreferencia("Retiros Espirituales", "https://images.unsplash.com/photo-1545389336-cf090694435e?w=400");
            crearPreferencia("Aromaterapia", "https://images.unsplash.com/photo-1608571423902-eed4a5ad8108?w=400");
            crearPreferencia("Pilates", "https://images.unsplash.com/photo-1518611012118-696072aa579a?w=400");
            crearPreferencia("Tai Chi", "https://images.unsplash.com/photo-1545205597-3d9d02c29597?w=400");
            crearPreferencia("Naturopatía", "https://images.unsplash.com/photo-1505751172876-fa1923c5c528?w=400");
            crearPreferencia("Terapias Holísticas", "https://images.unsplash.com/photo-1600334129128-685c5582fd35?w=400");
            
            log.info("Preferencias inicializadas correctamente. Total: " + preferenciasRepositorio.count());
        } else {
            log.info("Las preferencias ya están inicializadas. Total: " + preferenciasRepositorio.count());
        }
    }

    private void crearPreferencia(String nombre, String imagen) {
        try {
            Preferencias preferencia = new Preferencias();
            preferencia.setNombre(nombre);
            preferencia.setImagen(imagen);
            preferenciasRepositorio.save(preferencia);
            log.debug("Preferencia creada: {}", nombre);
        } catch (Exception e) {
            log.error("Error al crear preferencia {}: {}", nombre, e.getMessage());
        }
    }
}
