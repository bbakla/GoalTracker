package com.goaltracker.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SampleTestDataCreator {

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
