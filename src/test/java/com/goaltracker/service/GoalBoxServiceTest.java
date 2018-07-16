package com.goaltracker.service;

import static com.goaltracker.SampleTestDataCreator.createGoalContainerWithTwoGoalInBoxes;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
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
	
	@AfterEach
	public void cleanUp() {
		goalRepository.deleteAll();
		containerRepository.deleteAll();
	}
	
	@Test
	@Disabled
	void shouldAutomaticallySaveGoalsOfAGoalBoxlfTheyAreNotSavedAlready() {
		GoalBox container = createGoalContainerWithTwoGoalInBoxes();
		
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
	
	@Test
	void modificationOfAGoalShouldNotAffectTheSameGoalInAnotherGoalBox() {
		GoalBox goalBox = createGoalContainerWithTwoGoalInBoxes();
		goalRepository.saveAll(goalBox.getGoals());
		GoalBox savedGoalBox = containerRepository.save(goalBox);
		GoalBox goalBoxInRepo = containerRepository.findById(savedGoalBox.getId()).get();
		
		GoalBox container2 = createGoalContainerWithTwoGoalInBoxes();
		container2.setGoals(savedGoalBox.getGoals());
		GoalBox savedGoalBox2 = containerRepository.save(container2);
		
		container2.getGoals().get(0).setName("updated name");
		savedGoalBox2 = containerRepository.save(container2);
		GoalBox goalBox2InRepo = containerRepository.findById(savedGoalBox2.getId()).get();
		
		Goal goal = goalBoxInRepo.getGoals().get(0);
		Goal updatedGoalinBox2 = goalBox2InRepo.getGoals().get(0);
		
		assertNotEquals(goal.getName(), updatedGoalinBox2.getName());
	}

}
