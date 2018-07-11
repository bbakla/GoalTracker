package com.goaltracker.service;

import static com.goaltracker.SampleTestDataCreator.createGoalContainer;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.goaltracker.entity.Goal;
import com.goaltracker.entity.GoalBox;
import com.goaltracker.repository.GoalBoxRepository;
import com.goaltracker.repository.GoalRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class GoalBoxServiceTest {
	
	@Autowired
	private GoalBoxRepository containerRepository;

	@Autowired
	GoalRepository goalRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Test
	void shouldAutomaticallySaveGoalsOfAGoalBoxlfTheyAreNotSavedAlready() {
		GoalBox container = createGoalContainer();
		
//		List<Goal> goals = new ArrayList<>();
//		container.getGoals().forEach(goal -> {
//			Goal goalToBeSaved = mapper.map(goal, Goal.class);
//			goalRepository.save(goalToBeSaved);
//		});
		

//		List<Goal> savedGoals = goalRepository.saveAll(container.getGoals());
//		container.getGoals().removeIf(element -> element.getId() == null);
//		container.setGoals(savedGoals);

		GoalBox savedContainer = containerRepository.save(container);

		assertNotNull(savedContainer.getId());
		
		savedContainer.getGoals().forEach(goal -> {
			Goal goalInRepo = goalRepository.findByName(goal.getName());
			assertNotNull(goalInRepo.getId());
		});
	}


}
