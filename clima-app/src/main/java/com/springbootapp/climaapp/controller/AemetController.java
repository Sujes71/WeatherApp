package com.springbootapp.climaapp.controller;
import com.springbootapp.climaapp.model.Municipio;
import com.springbootapp.climaapp.model.dto.InformeDTO;
import com.springbootapp.climaapp.service.AemetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AemetController {

    @Autowired
    AemetService aemetService;

    @GetMapping("/municipios")
    public ResponseEntity<List<Municipio>> getMunicipios() {
        List<Municipio> municipios = this.aemetService.getMunicipios();

        if (!municipios.isEmpty()) {
            return ResponseEntity.ok(municipios);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/prediccion/{id_municipio}")
    public ResponseEntity<InformeDTO> getPrediccion(@PathVariable String id_municipio) {
        InformeDTO informeDTO = this.aemetService.getClimaMañana(id_municipio);

        if (informeDTO != null) {
            return ResponseEntity.ok(informeDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre-municipio/{id_municipio}")
    public ResponseEntity<String> getNombreMunicipioPorId(@PathVariable String id_municipio) {
        String nombre = this.aemetService.getNombreMunicipioPorId(id_municipio);

        if (!nombre.isEmpty()) {
            return ResponseEntity.ok(nombre);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/prediccion-municipio/{nombre}")
    public ResponseEntity<InformeDTO> getClimaMunicipioMañana(@PathVariable String nombre) {
        InformeDTO informeDTO = this.aemetService.getClimaMunicipioMañana(nombre);

        if (informeDTO != null) {
            return ResponseEntity.ok(informeDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
