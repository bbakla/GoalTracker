package com.goaltracker.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.goaltracker.SampleTestDataCreator;
import com.goaltracker.controller.resource.GoalResource;
import com.goaltracker.dto.GoalDto;
import com.goaltracker.entity.Goal;
import com.goaltracker.repository.GoalRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class GoalServiceTest {

	@Autowired
	private GoalService goalService;

	@Autowired
	private GoalRepository goalRepository;
	
	@AfterEach
	public void cleanUp() {
		goalRepository.deleteAll();
	}

	@Test
	void createAGoalUsingGoalDto() throws Exception {
		GoalDto sampleGoalDto = SampleTestDataCreator.createGoalDto();

		GoalResource resource = goalService.saveGoal(sampleGoalDto);

		Goal goalInRepo = goalRepository.findById(resource.getId()).get();

		assertNotNull(goalInRepo);
	}

	@Test
	void cannotNotCreateAGoalWithDublicateName() throws Exception {
		GoalDto sampleGoalDto = SampleTestDataCreator.createGoalDto();
		goalService.saveGoal(sampleGoalDto);

		GoalDto sampleGoalDto2 = SampleTestDataCreator.createGoalDto();
		assertThrows(DuplicateNameException.class, () -> {
			goalService.saveGoal(sampleGoalDto2);
		});
	}
	
	@Test
	void canGetListOfGoals() throws Exception {
		GoalDto sampleGoalDto = SampleTestDataCreator.createGoalDto();
		goalService.saveGoal(sampleGoalDto);
		
		GoalDto sampleGoalDto2 = SampleTestDataCreator.createGoalDto();
		sampleGoalDto2.setName("Name for SampleGoalDto2");
		goalService.saveGoal(sampleGoalDto2);
		
		List<GoalResource> goals = goalService.getAllGoals();
		
		assertTrue(goals.stream().map(goal -> goal.getId()) != null);
	}
	
	@Test
	void canGetASpecificGoalWithId() throws Exception{
		GoalDto sampleGoalDto = SampleTestDataCreator.createGoalDto();
		GoalResource savedGoal = goalService.saveGoal(sampleGoalDto);
		
		GoalResource goalInRepo = goalService.getGoalById(savedGoal.getId());
		
		assertEquals(sampleGoalDto.getName(), goalInRepo.getName());
	}
	
	@Test
	void canUpdateAGoal() throws Exception{
		
		GoalDto sampleGoalDto = SampleTestDataCreator.createGoalDto();
		GoalResource savedGoal = goalService.saveGoal(sampleGoalDto);
		
		sampleGoalDto.setName("Different name");
		GoalResource updatedGoal = goalService.updateGoal(savedGoal.getId(), sampleGoalDto);
		
		assertEquals(sampleGoalDto.getName(), updatedGoal.getName());
	}
}
