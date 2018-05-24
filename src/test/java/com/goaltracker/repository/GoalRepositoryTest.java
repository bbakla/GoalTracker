package com.goaltracker.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.goaltracker.entity.Goal;
import com.goaltracker.entity.SampleTestDataCreator;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class GoalRepositoryTest {

	@Autowired
	private GoalRepository goalRepository;
	
	@AfterEach
	public void cleanRepo() {
		goalRepository.deleteAll();
	}
	
	@Test
	void  canInsertAGoal() throws Exception {
		Goal goal = SampleTestDataCreator.createGoal();
		
//		ObjectMapper objectMapper = new ObjectMapper();
//		StringWriter stringEmp = new StringWriter();
//		objectMapper.writeValue(stringEmp, goal);
//		System.out.println(stringEmp);
//		
//		assumeNotNull(stringEmp);
		
		Goal savedGoal = goalRepository.save(goal);
		
		assertNotNull(savedGoal.getId());
	}
	
	@Test
	void canGetAllEntries() {
		Goal goal1 = SampleTestDataCreator.createGoal();
		Goal goal2 = SampleTestDataCreator.createGoal();
		
		goalRepository.save(goal1);
		goalRepository.save(goal2);
		
		List<Goal> goals =  goalRepository.findAll();
		
		assertTrue(goals.stream().map(goal -> goal.getId()) != null);
		
		
	}
}
