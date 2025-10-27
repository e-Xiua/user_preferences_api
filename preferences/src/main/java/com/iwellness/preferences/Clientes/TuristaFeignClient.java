package com.iwellness.preferences.Clientes;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.iwellness.preferences.DTO.TuristaDTO;

@EnableFeignClients
@FeignClient(name = "turista-ms", url = "${feign.client.turista.url:http://localhost:8082}/usuarios")
public interface TuristaFeignClient {

    @GetMapping("/buscar/{idUsuario}")
    TuristaDTO  obtenerTurista(@PathVariable("idUsuario") Long idUsuario);

}
