package com.goaltracker.repository;

import static com.goaltracker.SampleTestDataCreator.*;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.goaltracker.dto.GoalDto;
import com.goaltracker.entity.Goal;
import com.goaltracker.entity.GoalBox;
import com.goaltracker.entity.GoalInBox;
import com.goaltracker.entity.Status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class GoalBoxRepositoryTest {

	@Autowired
	private GoalBoxRepository containerRepository;

	@Autowired
	GoalRepository goalRepository;

	@AfterEach
	void cleanUp() {
		containerRepository.deleteAll();
	}

	@Test
	void canCreateAGoalBoxWithGoals() {
		GoalBox container = createGoalContainer();
		GoalBox savedContainer = containerRepository.save(container);

		assertNotNull(savedContainer.getId());
	}

	@Test
	void canAddAGoalToAnExistingBox() {
		GoalBox container = createGoalContainer();
		GoalBox savedContainer = containerRepository.save(container);

		GoalInBox goal = createGoalInBox();
		goal.setName("added After box is created");
		savedContainer.addGoal(goal);

		containerRepository.save(savedContainer);

		GoalBox updatedBox = containerRepository.findById(savedContainer.getId()).get();

		assertNotNull(updatedBox);
		assertEquals(savedContainer.getGoals().size(), updatedBox.getGoals().size());
	}

	@Test
	void canChangeStatusOfAGoalInABox() {
		GoalBox container = createGoalContainer();
		GoalBox savedContainer = containerRepository.save(container);

		savedContainer.getGoals().get(0).setStatus(Status.DONE);

		GoalBox updatedContainer = containerRepository.save(savedContainer);

		assertEquals(container.getGoals().size(), updatedContainer.getGoals().size());
		assertEquals(Status.DONE, updatedContainer.getGoals().get(0).getStatus());
	}

	@Test
	void canRemoveAGoalFromAnExistingBox() {
		GoalBox container = createGoalContainer();
		GoalBox savedContainer = containerRepository.save(container);

		savedContainer.getGoals().remove(0);

		GoalBox updatedContainer = containerRepository.save(savedContainer);

		assertEquals(savedContainer.getGoals().size(), updatedContainer.getGoals().size());
	}

	@Test
	void canGetATimeBox() {

	}

	@Test
	void canGetAllTimeBoxes() {

	}

	@Test
	void canDeleteATimeBox() {

	}

}
