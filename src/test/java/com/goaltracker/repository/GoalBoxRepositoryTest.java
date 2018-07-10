package com.goaltracker.repository;

import static com.goaltracker.SampleTestDataCreator.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.goaltracker.entity.Goal;
import com.goaltracker.entity.GoalBox;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class GoalBoxRepositoryTest {

	@Autowired
	private GoalBoxRepository containerRepository;
	
	@Autowired GoalRepository goalRepository;
	
	@AfterEach
	void cleanUp() {
		containerRepository.deleteAll();
	}

	@Test
	void canCreateAGoalBoxWithGoals() {
		GoalBox container = createGoalContainer();
		
		List<Goal> savedGoals = goalRepository.saveAll(container.getGoals());
		container.getGoals().removeIf(element -> element.getId() == null);
		container.setGoals(savedGoals);
		
		GoalBox savedContainer = containerRepository.save(container);
		
		assertNotNull(savedContainer.getId());
	}

}
