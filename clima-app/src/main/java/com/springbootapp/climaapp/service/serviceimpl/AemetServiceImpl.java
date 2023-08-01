package com.springbootapp.climaapp.service.serviceimpl;

import com.springbootapp.climaapp.model.DatosClima;
import com.springbootapp.climaapp.model.Dia;
import com.springbootapp.climaapp.model.InformeClima;
import com.springbootapp.climaapp.model.Municipio;
import com.springbootapp.climaapp.model.dto.InformeDTO;
import com.springbootapp.climaapp.service.AemetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class AemetServiceImpl implements AemetService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${aemet.api-key}")
    private String apiKey;

    private final Map<String, List<Municipio>> municipiosCache = new ConcurrentHashMap<>();

    @Override
    public List<Municipio> getMunicipios() {
        String url = "https://opendata.aemet.es/opendata/api/maestro/municipios?api_key=" + apiKey;
        if (municipiosCache.containsKey(url)) {
            List<Municipio> cachedMunicipios = municipiosCache.get(url);
            return cachedMunicipios;
        }
        ResponseEntity<Municipio[]> response = restTemplate.exchange(url, HttpMethod.GET, null, Municipio[].class);
        List<Municipio> municipios = Arrays.asList(response.getBody());
        municipiosCache.put(url, municipios);

        return municipios;
    }

    @Override
    public String getNombreMunicipioPorId(String id) {
        String url = "https://opendata.aemet.es/opendata/api/maestro/municipio/" + id + "?api_key=" + apiKey;
        ResponseEntity<Municipio[]> response = restTemplate.exchange(url, HttpMethod.GET, null, Municipio[].class);
        Municipio municipio = response.getBody()[0];

        return municipio.getNombre();
    }

    @Override
    public InformeDTO getClimaMañana(String id_municipio) {
        String url = "https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/" + id_municipio + "?api_key=" + apiKey;
        DatosClima datos = null;
        Dia dia = null;
        int manana = LocalDate.now().plusDays(1).getDayOfMonth();

        ResponseEntity<DatosClima> responseDatos = restTemplate.exchange(url, HttpMethod.GET, null, DatosClima.class);
        datos = responseDatos.getBody();

        ResponseEntity<InformeClima[]> response = restTemplate.exchange(datos.getDatos(), HttpMethod.GET, null, InformeClima[].class);
        dia = response.getBody()[0].getPrediccion().getDia().stream().filter(
                diaI -> manana == diaI.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfMonth()
        ).collect(Collectors.toList()).get(0);

        InformeDTO informeDTO = new InformeDTO();

        if(dia.getProbPrecipitacion().size() > 3)
            dia.getProbPrecipitacion().subList(0, 3).clear();

        informeDTO.setProbPrecipitacion(dia.getProbPrecipitacion());
        informeDTO.setMediaTemperatura((dia.getTemperatura().getMaxima() + dia.getTemperatura().getMinima()) / 2);
        informeDTO.setFecha(dia.getFecha());

        return informeDTO;
    }

    @Override
    public InformeDTO getClimaMunicipioMañana(String nombre) {
        List<Municipio> municipios = this.getMunicipios();
        Municipio municipioEncontrado = null;
        InformeDTO informeDTO = null;

        try{
            municipioEncontrado = municipios.stream()
                    .filter(municipio -> municipio.getNombre().equalsIgnoreCase(nombre))
                    .collect(Collectors.toList()).get(0);
        }catch(Exception e){}

        if(municipioEncontrado != null){
            informeDTO = this.getClimaMañana(municipioEncontrado.getId().substring(2));
            informeDTO.setNombre(municipioEncontrado.getNombre());
        }

        return informeDTO;
    }
}