package com.iwellness.preferences.Clientes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.iwellness.preferences.DTO.ServicioDTO;

@FeignClient(name = "servicio-ms", url = "${feign.client.servicio.url:http://localhost:8080}/api/servicio")
public interface ServicioFeignClient {

    @GetMapping("/search/{idServicio}")
    ServicioDTO  obtenerServicio(@PathVariable("idServicio") Long idServicio);
}
