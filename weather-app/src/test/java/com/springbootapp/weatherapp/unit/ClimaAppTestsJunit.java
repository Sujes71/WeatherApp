package com.springbootapp.weatherapp.unit;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.springbootapp.weatherapp.model.DayData;
import com.springbootapp.weatherapp.model.Municipality;
import com.springbootapp.weatherapp.model.ProbPrecipitation;
import com.springbootapp.weatherapp.model.Temperature;
import com.springbootapp.weatherapp.model.dto.ReportDTO;
import com.springbootapp.weatherapp.model.mapper.ReportMapper;
import com.springbootapp.weatherapp.service.component.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
class ClimaAppTestsJunit {

	@Value("${aemet.muns.url-localhost}")
	private String url;
	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Test
	public void testGetMuns() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtTokenUtil.generateToken(jwtTokenUtil.generateRandomCode()));

		HttpEntity<?> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<Municipality[]> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Municipality[].class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertTrue(response.getBody().length > 0);
	}

	@Test
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

		assertEquals(reportDTO.getName(), mun.getName());
		assertEquals(reportDTO.getDate(), dayData.getDate());
		assertEquals(reportDTO.getTemUnit(), "G_CEL");
		assertEquals(reportDTO.getTemAvg(), 25);

        assertEquals(1, reportDTO.getProbPrecipitations().size());
		dayData.setProbPrecipitations(probPrecipitationList2);
		reportDTO = ReportMapper.INSTANCE.dayToReportDTO(dayData);
		assertEquals(reportDTO.getProbPrecipitations(), dayData.getProbPrecipitations());

	}

	@Test
	void testHazelcastCache(@Autowired HazelcastInstance hazelcastInstance){
		IMap<String, List<Municipality>> munsCache = hazelcastInstance.getMap("map");
		assertTrue(munsCache.isEmpty());
		List<Municipality> muns = new ArrayList<>();
		Municipality mun1 = new Municipality();
		mun1.setName("Jesus");
		mun1.setId("1234");

		Municipality mun2 = new Municipality();
		mun2.setId("123");
		mun2.setName("Test");

		muns.add(mun1);
		muns.add(mun2);

		munsCache.put("Test", muns);

        assertEquals(1, munsCache.size());

        assertEquals("Jesus", munsCache.get("Test").get(0).getName());
	}

}
