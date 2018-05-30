package com.goaltracker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.goaltracker.dto.GoalDto;
import com.goaltracker.entity.Goal;
import com.goaltracker.entity.GoalContainer;
import com.goaltracker.entity.Scope;
import com.goaltracker.entity.Status;

public class SampleTestDataCreator {

	public static GoalDto createGoalDto() {
		GoalDto goal = new GoalDto();
		goal.setName("Sample Goal Dto");
		goal.setScope(Scope.DAILY);
		goal.setStatus(Status.NOT_DONE);
		
		return goal;
	}
	
	public static Goal createGoal() {
		Goal goal = new Goal();
		goal.setCreatedAt(LocalDate.now());
		goal.setName("Sample Goal");
		goal.setScope(Scope.DAILY);
		goal.setStartedAt(LocalDate.now());
		goal.setStatus(Status.NOT_DONE);
		
		return goal;
	}
	
	public static GoalContainer createGoalContainer() {
		GoalContainer container = new GoalContainer();
		container.setDate(LocalDate.now());
		container.setScope(Scope.DAILY);
		
		List<Goal> goals = new ArrayList<>();
		Goal goal = createGoal();
		goals.add(goal);
		goal = createGoal();
		goal.setName("SecondSampleGoal");
		goals.add(goal);
		
		container.setGoals(goals);
		
		return container;
	}
}
