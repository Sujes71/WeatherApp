package com.springbootapp.weatherapp.service.serviceimpl;

import com.springbootapp.weatherapp.model.*;
import com.springbootapp.weatherapp.model.dto.ForecastDTO;
import com.springbootapp.weatherapp.model.dto.TemperatureDTO;
import com.springbootapp.weatherapp.model.mapper.ReportMapper;
import com.springbootapp.weatherapp.service.AemetService;
import com.springbootapp.weatherapp.service.component.HazelCastUtil;
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
import java.util.stream.Collectors;

import static com.springbootapp.weatherapp.enums.TemperatureEnum.TEMPERATURE_CEL;
import static com.springbootapp.weatherapp.enums.TemperatureEnum.TEMPERATURE_FAH;

@Service
public class AemetServiceImpl implements AemetService {

    @Value("${aemet.api-key}")
    private String apiKey;

    @Value("${aemet.url-prediction}")
    private String prediction;

    @Value("${aemet.url-master}")
    private String master;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HazelCastUtil hazelCastUtil;

    @Override
    public List<Municipality> getMunicipalities() {
        String url = String.format("%ss%s", master, apiKey);
        if (hazelCastUtil.getMunCache().containsKey(url)) {
            return hazelCastUtil.getMunCache().get(url);
        }
        ResponseEntity<Municipality[]> response = restTemplate.exchange(url, HttpMethod.GET, null, Municipality[].class);
        List<Municipality> muns = Arrays.asList(response.getBody());
        hazelCastUtil.getMunCache().put(url, muns);

        return muns;
    }

    @Override
    public Municipality getMunById(String id) {
        String url = String.format("%s/%s%s", master, id, apiKey);
        ResponseEntity<Municipality[]> response = restTemplate.exchange(url, HttpMethod.GET, null, Municipality[].class);
        Municipality mun = response.getBody()[0];

        return mun;
    }

    @Override
    public ForecastDTO getMunTomorrowForecast(String id) {
        String url = String.format("%s%s%s", prediction, id.substring(2), apiKey);
        WData wData;
        DayData dayData = null;
        ForecastDTO forecastDTO = null;
        int tomorrow = LocalDate.now().plusDays(1).getDayOfMonth();

        if(isMunFound(id)){
            ResponseEntity<WData> responseDatos = restTemplate.exchange(url, HttpMethod.GET, null, WData.class);
            wData = responseDatos.getBody();

            ResponseEntity<WReport[]> response = restTemplate.exchange(wData.getData(), HttpMethod.GET, null, WReport[].class);
            try{
                dayData = response.getBody()[0].getPrediction().getDay().stream().filter(
                        diaI -> tomorrow == diaI.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfMonth()
                ).collect(Collectors.toList()).get(0);
            }catch (Exception e){}

            forecastDTO = ReportMapper.INSTANCE.dayToForecastDTO(dayData);
            ReportMapper.INSTANCE.updateForecastDTOFromMun(forecastDTO, this.getMunById(id));
        }

        return forecastDTO;
    }

    @Override
    public TemperatureDTO getConversion(Float avg, String unit) {
        TemperatureDTO temperatureDTO = new TemperatureDTO();
        if(unit.equals(TEMPERATURE_CEL.getUnit())){
            temperatureDTO.setUnit(TEMPERATURE_CEL.getUnit());
            temperatureDTO.setAvg((float) Math.round((avg - 32) * 5 / 9));
        } else {
            temperatureDTO.setUnit(TEMPERATURE_FAH.getUnit());
            temperatureDTO.setAvg((float) Math.round((avg * 9 / 5) + 32));
        }
        return temperatureDTO;
    }

    public boolean isMunFound(String id){
        List<Municipality> muns = this.getMunicipalities();
        Municipality munFound = null;
        try{
            munFound = muns.stream()
                    .filter(municipio -> municipio.getId().equalsIgnoreCase(id))
                    .collect(Collectors.toList()).get(0);
        }catch(Exception e){}

        return munFound != null;
    }
}