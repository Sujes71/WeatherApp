package com.springbootapp.weatherapp.model.mapper;

import com.springbootapp.weatherapp.model.DayData;
import com.springbootapp.weatherapp.model.Municipality;
import com.springbootapp.weatherapp.model.dto.ReportDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReportMapper {
    ReportMapper INSTANCE = Mappers.getMapper(ReportMapper.class);

    @Mapping(target = "name", source = "name")
    void updateNameReportDTOFromMun(@MappingTarget ReportDTO reportDTO, Municipality mun);

    @Mapping(source = "date", target = "date")
    @Mapping(target = "probPrecipitations", expression = "java(dayData.getProbPrecipitations().size() > 3 ? dayData.getProbPrecipitations().subList(3, dayData.getProbPrecipitations().size()) : dayData.getProbPrecipitations())")
    @Mapping(target = "temAvg", expression = "java((double) Math.round((dayData.getTemperature().getMax() + dayData.getTemperature().getMin()) / 2.0))")
    ReportDTO dayToReportDTO(DayData dayData);

}
