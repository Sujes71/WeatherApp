package com.springbootapp.weatherapp.service.serviceimpl;

import com.springbootapp.weatherapp.model.dto.ElementDTO;
import com.springbootapp.weatherapp.model.entity.ForecastEntity;
import com.springbootapp.weatherapp.model.entity.MunicipalityEntity;
import com.springbootapp.weatherapp.model.entity.ProbPrecipitationEntity;
import com.springbootapp.weatherapp.repository.ForecastRepository;
import com.springbootapp.weatherapp.repository.MunicipalityRepository;
import com.springbootapp.weatherapp.service.ForecastService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Profile("prod")
public class ForecastServiceImpl implements ForecastService {

    @Autowired
    ForecastRepository forecastRepository;
    @Autowired
    MunicipalityRepository municipalityRepository;

    @Override
    public List<ElementDTO> getForecasts() {
        List<ForecastEntity> forecastEntityData = forecastRepository
                .findAll(Sort.by(Sort.Order.asc("id")));
        List<ElementDTO> elementDTOS = new ArrayList<>();

        if (!forecastEntityData.isEmpty()) {
            for(ForecastEntity forecastEntity: forecastEntityData){
                Float prob_precipitation = 0.00F;
                for (ProbPrecipitationEntity prob : forecastEntity.getProbPrecipitations()){
                    prob_precipitation += prob.getValue();
                }

                prob_precipitation = prob_precipitation / forecastEntity.getProbPrecipitations().size();
                ElementDTO elementDTO = ElementDTO
                        .builder()
                        .id(forecastEntity.getId())
                        .municipality(forecastEntity.getMunicipality())
                        .date(forecastEntity.getDate())
                        .avg(forecastEntity.getTemperature().getAvg())
                        .unit(forecastEntity.getTemperature().getUnit())
                        .precipitation_avg(prob_precipitation)
                        .build();
                elementDTOS.add(elementDTO);
            }
            return elementDTOS;
        }
        return null;
    }
    @Override
    public List<ForecastEntity> getForecastsByMunicipality(String name) {
        List<ForecastEntity> forecastEntityData = forecastRepository
                .findByMunicipality(name);

        if (!forecastEntityData.isEmpty()) {
            return forecastEntityData;
        }
        return null;
    }
    @Override
    public ForecastEntity addForecast(@Valid @RequestBody ForecastEntity forecastEntity) {
        MunicipalityEntity municipality = this.municipalityRepository
                .findByName(forecastEntity.getMunicipality());
        if (municipality == null) {
            municipalityRepository
                    .save(new MunicipalityEntity(forecastEntity.getMunicipality()));
        }
        try {
            forecastRepository.save(forecastEntity);
            return forecastEntity;
        }catch(Exception e){
            return null;
        }
    }
    @Override
    public void deleteById(Long id) {
        this.forecastRepository.deleteById(id);
    }
    @Override
    public ForecastEntity getForecastById(Long id) {
        Optional<ForecastEntity> result = this.forecastRepository
                .findById(id);
        return result.orElse(null);
    }
}
