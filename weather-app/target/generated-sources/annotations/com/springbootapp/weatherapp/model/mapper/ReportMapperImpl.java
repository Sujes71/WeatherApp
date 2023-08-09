package com.springbootapp.weatherapp.model.mapper;

import com.springbootapp.weatherapp.model.DayData;
import com.springbootapp.weatherapp.model.Municipality;
import com.springbootapp.weatherapp.model.dto.ReportDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-09T02:40:21+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
public class ReportMapperImpl implements ReportMapper {

    @Override
    public void updateNameReportDTOFromMun(ReportDTO reportDTO, Municipality mun) {
        if ( mun == null ) {
            return;
        }

        reportDTO.setName( mun.getName() );
    }

    @Override
    public ReportDTO dayToReportDTO(DayData dayData) {
        if ( dayData == null ) {
            return null;
        }

        ReportDTO reportDTO = new ReportDTO();

        reportDTO.setDate( dayData.getDate() );

        reportDTO.setProbPrecipitations( dayData.getProbPrecipitations().size() > 3 ? dayData.getProbPrecipitations().subList(3, dayData.getProbPrecipitations().size()) : dayData.getProbPrecipitations() );
        reportDTO.setTemAvg( (double) Math.round((dayData.getTemperature().getMax() + dayData.getTemperature().getMin()) / 2.0) );

        return reportDTO;
    }
}
