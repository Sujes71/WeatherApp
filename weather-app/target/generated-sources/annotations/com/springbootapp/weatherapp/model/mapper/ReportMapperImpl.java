package com.springbootapp.weatherapp.model.mapper;

import com.springbootapp.weatherapp.model.DayData;
import com.springbootapp.weatherapp.model.Municipality;
import com.springbootapp.weatherapp.model.dto.ForecastDTO;
import com.springbootapp.weatherapp.model.dto.TemperatureDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-14T16:08:03+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
public class ReportMapperImpl implements ReportMapper {

    @Override
    public void updateForecastDTOFromMun(ForecastDTO forecastDTO, Municipality mun) {
        if ( mun == null ) {
            return;
        }

        forecastDTO.setMunicipality( mun );
    }

    @Override
    public ForecastDTO dayToForecastDTO(DayData dayData) {
        if ( dayData == null ) {
            return null;
        }

        ForecastDTO forecastDTO = new ForecastDTO();

        forecastDTO.setDate( dayData.getDate() );
        forecastDTO.setTemperature( dayDataToTemperatureDTO( dayData ) );

        forecastDTO.setProbPrecipitations( dayData.getProbPrecipitations().size() > 3 ? dayData.getProbPrecipitations().subList(3, dayData.getProbPrecipitations().size()) : dayData.getProbPrecipitations() );

        return forecastDTO;
    }

    protected TemperatureDTO dayDataToTemperatureDTO(DayData dayData) {
        if ( dayData == null ) {
            return null;
        }

        TemperatureDTO temperatureDTO = new TemperatureDTO();

        temperatureDTO.setAvg( Float.valueOf(Math.round((dayData.getTemp().getMax() + dayData.getTemp().getMin()) / 2.0)) );
        temperatureDTO.setUnit( setUnit() );

        return temperatureDTO;
    }
}
