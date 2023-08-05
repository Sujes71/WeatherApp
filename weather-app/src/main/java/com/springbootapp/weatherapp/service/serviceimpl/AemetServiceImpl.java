package com.springbootapp.weatherapp.service.serviceimpl;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.springbootapp.weatherapp.model.DayData;
import com.springbootapp.weatherapp.model.Municipality;
import com.springbootapp.weatherapp.model.WData;
import com.springbootapp.weatherapp.model.WReport;
import com.springbootapp.weatherapp.model.dto.ReportDTO;
import com.springbootapp.weatherapp.model.dto.TemperatureDTO;
import com.springbootapp.weatherapp.model.mapper.ReportMapper;
import com.springbootapp.weatherapp.service.AemetService;
import jakarta.annotation.PostConstruct;
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

@Service
public class AemetServiceImpl implements AemetService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${aemet.api-key}")
    private String apiKey;

    @Value("${aemet.url-prediction}")
    private String prediction;

    @Value("${aemet.url-master}")
    private String master;

    @Autowired
    private HazelcastInstance hazelcastInstance;
    private IMap<String, List<Municipality>> munCache;

    @PostConstruct
    public void init() {
        munCache = hazelcastInstance.getMap("map");
    }

    @Override
    public List<Municipality> getMuns() {
        String url = String.format("%ss%s", master, apiKey);
        if (munCache.containsKey(url)) {
            return munCache.get(url);
        }
        ResponseEntity<Municipality[]> response = restTemplate.exchange(url, HttpMethod.GET, null, Municipality[].class);
        List<Municipality> muns = Arrays.asList(response.getBody());
        munCache.put(url, muns);

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
    public ReportDTO getPredictMunTomorrow(String id) {
        String url = String.format("%s%s%s", prediction, id.substring(2), apiKey);
        WData wData = null;
        DayData dayData = null;
        ReportDTO reportDTO = null;
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

            reportDTO = ReportMapper.INSTANCE.dayToReportDTO(dayData);
            ReportMapper.INSTANCE.updateNameReportDTOFromMun(reportDTO, this.getMunById(id));
        }

        return reportDTO;
    }

    @Override
    public TemperatureDTO getConversion(Float avg, String unit) {
        TemperatureDTO temperatureDTO = new TemperatureDTO();
        if(unit.equals("G_CEL")){
            temperatureDTO.setUnit("G_CEL");
            temperatureDTO.setAvg((float) Math.round((avg - 32) * 5 / 9));
        } else {
            temperatureDTO.setUnit("G_FAH");
            temperatureDTO.setAvg((float) Math.round((avg * 9 / 5) + 32));
        }
        return temperatureDTO;
    }

    public boolean isMunFound(String id){
        List<Municipality> muns = this.getMuns();
        Municipality munFound = null;
        ReportDTO reportDTO = null;
        try{
            munFound = muns.stream()
                    .filter(municipio -> municipio.getId().equalsIgnoreCase(id))
                    .collect(Collectors.toList()).get(0);
        }catch(Exception e){}

        return munFound != null;
    }
}