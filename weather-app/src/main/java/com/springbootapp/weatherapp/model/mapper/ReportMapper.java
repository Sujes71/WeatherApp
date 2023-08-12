package com.springbootapp.weatherapp.model.mapper;

import com.springbootapp.weatherapp.model.DayData;
import com.springbootapp.weatherapp.model.Municipality;
import com.springbootapp.weatherapp.model.dto.ForecastDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReportMapper {
    ReportMapper INSTANCE = Mappers.getMapper(ReportMapper.class);

    @Mapping(target = "municipality", source = "mun")
    void updateForecastDTOFromMun(@MappingTarget ForecastDTO forecastDTO, Municipality mun);


    @Mapping(source = "date", target = "date")
    @Mapping(target = "probPrecipitations", expression = "java(dayData.getProbPrecipitations().size() > 3 ? dayData.getProbPrecipitations().subList(3, dayData.getProbPrecipitations().size()) : dayData.getProbPrecipitations())")
    @Mapping(target = "temperature.avg", expression = "java(Float.valueOf(Math.round((dayData.getTemp().getMax() + dayData.getTemp().getMin()) / 2.0)))")
    @Mapping(target = "temperature.unit", expression = "java(setUnit())")
    ForecastDTO dayToForecastDTO(DayData dayData);

    default String setUnit() {
        return "G_CEL";
    }

}
