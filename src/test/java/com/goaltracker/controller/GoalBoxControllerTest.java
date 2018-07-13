package com.goaltracker.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.goaltracker.SampleTestDataCreator.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.goaltracker.dto.GoalBoxDto;
import com.goaltracker.dto.GoalDto;
import com.goaltracker.entity.GoalBox;
import com.goaltracker.entity.GoalInBox;
import com.goaltracker.entity.Status;
import com.goaltracker.entity.TimeFrame;
import com.goaltracker.repository.GoalBoxRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class GoalBoxControllerTest {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private GoalBoxRepository goalBoxRepository;

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
		goalBoxRepository.deleteAll();
	}

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

	@Test
	void canCreateGoalBox() throws Exception {
		GoalBox goalBox = createGoalContainer();

		mockMvc.perform(post(Constants.GOAL_BOX_CONTROLLER_PATH)
				.contentType(contentType)
				.content(json(goalBox)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", notNullValue()));
	}

	@Test
	void canGetAGoalBox() throws Exception {
		GoalBox goalBox = createGoalContainer();
		GoalBox savedGoalBox = goalBoxRepository.save(goalBox);
		
		mockMvc.perform(get(Constants.GOAL_BOX_CONTROLLER_PATH + "/"+ savedGoalBox.getId()))
			   .andExpect(status().isOk())
			   .andExpect(jsonPath("$.id", equalTo(savedGoalBox.getId())))
			   .andExpect(jsonPath("$.scope", equalTo(savedGoalBox.getScope().toString())));
	}

	@Test
	void canGetAllBoxes() throws Exception {
		GoalBox goalBox = createGoalContainer();
		goalBoxRepository.save(goalBox);
		
		GoalBox goalBox2 = createGoalContainer();
		GoalBox inRepo = goalBoxRepository.save(goalBox2);
		
		mockMvc.perform(get(Constants.GOAL_BOX_CONTROLLER_PATH))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[1].id", equalTo(inRepo.getId())));
	}

	@Test
	void canDeleteAGoalBox() throws Exception{
		GoalBox goalBox = createGoalContainer();
		GoalBox savedGoalBox = goalBoxRepository.save(goalBox);
		
		mockMvc.perform(delete(Constants.GOAL_BOX_CONTROLLER_PATH + "/" + savedGoalBox.getId()))
				.andExpect(status().isNoContent());
	}

	@Test
	void canUpdateAGoalBox() throws Exception{
		GoalBox goalBox = createGoalContainer();
		GoalBox savedGoalBox = goalBoxRepository.save(goalBox);
		
		savedGoalBox.setScope(TimeFrame.WEEKLY);
		
		mockMvc.perform(put(Constants.GOAL_BOX_CONTROLLER_PATH + "/" + savedGoalBox.getId())
				.contentType(contentType)
				.content(json(savedGoalBox)))
		.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.scope", equalTo(savedGoalBox.getScope().toString())));
	}

	@Test
	void canUpdateAGoalInAGoalBox() throws Exception{
		GoalBox goalBox = createGoalContainer();
		GoalBox savedGoalBox = goalBoxRepository.save(goalBox);
		
		GoalInBox goalToBeUpdated = savedGoalBox.getGoals().get(0);
		goalToBeUpdated.setStatus(Status.DONE);
		
		GoalDto goalDto = new GoalDto();
		goalDto.setName(goalToBeUpdated.getName());
		goalDto.setStatus(goalToBeUpdated.getStatus());
		
		mockMvc.perform(patch(Constants.GOAL_BOX_CONTROLLER_PATH + "/" + 
							savedGoalBox.getId() + "/goals/" + goalToBeUpdated.getName())
				.contentType(contentType)
				.content(json(goalDto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", equalTo(goalToBeUpdated.getName())))
				.andExpect(jsonPath("$.status", equalTo(goalToBeUpdated.getStatus().toString())));
	}

}
