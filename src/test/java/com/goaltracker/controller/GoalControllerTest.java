package com.goaltracker.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static com.goaltracker.SampleTestDataCreator.*;
import static com.goaltracker.controller.Constants.*; 

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.goaltracker.dto.GoalDto;
import org.springframework.context.support.StaticApplicationContext; 
import com.goaltracker.repository.GoalBoxRepository;
import com.goaltracker.repository.GoalRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class GoalControllerTest {
	
	@Autowired
	private GoalBoxRepository containerRepository;
	
	@Autowired
	private GoalRepository goalRepository;
	
	@Autowired
	private MockMvc mockMvc;
	
	private HttpMessageConverter mappingJackson2HttpMessageConverter;
	
	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

		assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}
	
	protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	@AfterEach
	public void cleanUp() {
		goalRepository.deleteAll();
		containerRepository.deleteAll();
	}
	
	@Test
	void canCreateGoal() throws Exception{
		
		GoalDto goalDto = createGoalDto();
		
		mockMvc.perform(post(GOAL_CONTROLLER_PATH)
				.contentType(contentType)
				.content(json(goalDto)))
				.andExpect(status().isCreated())
				.andExpect((jsonPath("$.id", notNullValue())))
				.andExpect(jsonPath("$.name", equalTo(goalDto.getName())));
	}
	
	@Test
	void canUpdateAGoal() {
		
	}
	
	@Test
	void canDeleteAGoalIfItIsNotInAnyContainer() {
		
	}
	
	@Test
	void canGetAGoal() {
		
	}
	
	@Test
	void canGetAllActiveGoals() {
		
	}
	
	
	
	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
}
