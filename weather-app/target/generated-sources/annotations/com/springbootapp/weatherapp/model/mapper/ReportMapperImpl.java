package com.springbootapp.weatherapp.model.mapper;

import com.springbootapp.weatherapp.model.DayData;
import com.springbootapp.weatherapp.model.Municipality;
import com.springbootapp.weatherapp.model.dto.ForecastDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-10T22:51:56+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
public class ReportMapperImpl implements ReportMapper {

    @Override
    public void updateNameForecastDTOFromMun(ForecastDTO forecastDTO, Municipality mun) {
        if ( mun == null ) {
            return;
        }

        forecastDTO.setName( mun.getName() );
    }

    @Override
    public ForecastDTO dayToForecastDTO(DayData dayData) {
        if ( dayData == null ) {
            return null;
        }

        ForecastDTO forecastDTO = new ForecastDTO();

        forecastDTO.setDate( dayData.getDate() );

        forecastDTO.setProbPrecipitations( dayData.getProbPrecipitations().size() > 3 ? dayData.getProbPrecipitations().subList(3, dayData.getProbPrecipitations().size()) : dayData.getProbPrecipitations() );
        forecastDTO.setAvg( (double) Math.round((dayData.getTemperature().getMax() + dayData.getTemperature().getMin()) / 2.0) );

        return forecastDTO;
    }
}
