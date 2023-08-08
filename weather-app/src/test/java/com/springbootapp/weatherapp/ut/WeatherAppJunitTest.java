package com.springbootapp.weatherapp.ut;


import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.springbootapp.weatherapp.WeatherAppApplication;
import com.springbootapp.weatherapp.model.DayData;
import com.springbootapp.weatherapp.model.Municipality;
import com.springbootapp.weatherapp.model.ProbPrecipitation;
import com.springbootapp.weatherapp.model.Temperature;
import com.springbootapp.weatherapp.model.dto.ReportDTO;
import com.springbootapp.weatherapp.model.mapper.ReportMapper;
import com.springbootapp.weatherapp.service.AemetService;
import com.springbootapp.weatherapp.service.component.HazelCastUtil;
import com.springbootapp.weatherapp.service.serviceimpl.AemetServiceImpl;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.hazelcast.jet.core.test.JetAssert.assertEquals;
import static com.hazelcast.jet.core.test.JetAssert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Tag("units")
@SpringBootTest
class WeatherAppJunitTest {
	@Autowired
	HazelCastUtil hazelCastUtil;
	@InjectMocks
	private AemetServiceImpl aemetService;
	@Mock
	private RestTemplate mockRestTemplate;
	@Mock
	private IMap<String, List<Municipality>> mockImap;
	@Mock
	private HazelCastUtil mockHazelCastUtil;

	@Test
	void testGetNameMunById() {
		Municipality mockMun = new Municipality();
		mockMun.setName("Ababuj");
		mockMun.setId("id44001");
		ResponseEntity<Municipality[]> responseEntity =
				new ResponseEntity<>(new Municipality[]{mockMun}, HttpStatus.OK);
		when(mockRestTemplate.exchange(
				anyString(),
				eq(HttpMethod.GET),
				eq(null),
				eq(Municipality[].class)))
				.thenReturn(responseEntity);

		Municipality mun = aemetService.getMunById("id44001");

		Assertions.assertEquals("Ababuj", mun.getName());
	}

	@Test
	@DisplayName("Returned all Municipalities?")
	public void testGetMuns() throws Exception {
		Municipality mun1 = new Municipality();
		mun1.setId("id44001");
		mun1.setName("Ababuj");

		Municipality mun2 = new Municipality();
		mun2.setId("id23432");
		mun2.setName("Marbella");

		when(mockHazelCastUtil.getMunCache()).thenReturn(mockImap);

		List<Municipality> munsI = new ArrayList<>();
		munsI.add(mun1);
		munsI.add(mun2);

		when(mockImap.get(anyString())).thenReturn(munsI);

		ResponseEntity<Municipality[]> responseEntity =
				new ResponseEntity<>(new Municipality[]{mun1, mun2}, HttpStatus.OK);
		when(mockRestTemplate.exchange(
				anyString(),
				eq(HttpMethod.GET),
				eq(null),
				eq(Municipality[].class)))
				.thenReturn(responseEntity);

		List<Municipality> municipalities = this.aemetService.getMuns();
		assertTrue("Mismo tamaño", municipalities.size() == 2);
	}


	@Test
	@DisplayName("Is MapStruct working fine?")
	void testMapStruct(){
		Municipality mun = new Municipality();
		mun.setId("12345");
		mun.setName("Jesus");

		DayData dayData =  new DayData();
		dayData.setDate(new Date());

		Temperature temperature = new Temperature();
		temperature.setMin(20.5);
		temperature.setMax(30.2);

		ProbPrecipitation probPrecipitation = new ProbPrecipitation();
		probPrecipitation.setPeriod("00-12");
		probPrecipitation.setValue(0);

		ProbPrecipitation probPrecipitation2 = new ProbPrecipitation();
		probPrecipitation2.setPeriod("12-23");
		probPrecipitation2.setValue(15);

		ProbPrecipitation probPrecipitation3 = new ProbPrecipitation();
		probPrecipitation3.setPeriod("23-31");
		probPrecipitation3.setValue(30);

		ProbPrecipitation probPrecipitation4 = new ProbPrecipitation();
		probPrecipitation4.setPeriod("31-2023");
		probPrecipitation4.setValue(30);

		List<ProbPrecipitation> probPrecipitationList = new ArrayList<>();
		probPrecipitationList.add(probPrecipitation);
		probPrecipitationList.add(probPrecipitation2);
		probPrecipitationList.add(probPrecipitation3);
		probPrecipitationList.add(probPrecipitation4);

		List<ProbPrecipitation> probPrecipitationList2 = new ArrayList<>();
		probPrecipitationList2.add(probPrecipitation3);
		probPrecipitationList2.add(probPrecipitation4);

		dayData.setProbPrecipitations(probPrecipitationList);
		dayData.setTemperature(temperature);

		ReportDTO reportDTO = ReportMapper.INSTANCE.dayToReportDTO(dayData);
		ReportMapper.INSTANCE.updateNameReportDTOFromMun(reportDTO, mun);

		assertEquals("Name Mun" ,reportDTO.getName(), mun.getName());
		assertEquals("Date", reportDTO.getDate(), dayData.getDate());
		assertEquals("Unit", reportDTO.getTemUnit(), "G_CEL");
		assertEquals("Avg", reportDTO.getTemAvg(), 25.0);

        assertEquals("Nº elementos probPrepitation", 1, reportDTO.getProbPrecipitations().size());
		dayData.setProbPrecipitations(probPrecipitationList2);
		reportDTO = ReportMapper.INSTANCE.dayToReportDTO(dayData);
		assertEquals("ProbPre", reportDTO.getProbPrecipitations(), dayData.getProbPrecipitations());

	}

	@Test
	@DisplayName("Is hazelCastCache working fine?")
	void testHazelcastCache(){

		assertEquals("Cache is empty",hazelCastUtil.getMunCache().isEmpty(), true);
		List<Municipality> muns = new ArrayList<>();
		Municipality mun1 = new Municipality();
		mun1.setName("Jesus");
		mun1.setId("1234");

		Municipality mun2 = new Municipality();
		mun2.setId("123");
		mun2.setName("Test");

		muns.add(mun1);
		muns.add(mun2);

		hazelCastUtil.getMunCache().put("Test", muns);

        assertEquals("Cache has 1 element", 1, hazelCastUtil.getMunCache().size());

        assertEquals("Cache has Jesus in first position", "Jesus", hazelCastUtil.getMunCache().get("Test").get(0).getName());
	}

}
