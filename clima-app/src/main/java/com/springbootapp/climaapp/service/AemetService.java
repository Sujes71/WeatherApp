package com.springbootapp.climaapp.service;

import com.springbootapp.climaapp.model.Dia;
import com.springbootapp.climaapp.model.Municipio;
import com.springbootapp.climaapp.model.dto.InformeDTO;

import java.util.List;

public interface AemetService {

    List<Municipio> getMunicipios();
    String getNombreMunicipioPorId(String id);
    InformeDTO getClimaMañana(String id_municipio);
    InformeDTO getClimaMunicipioMañana(String nombre);
}
