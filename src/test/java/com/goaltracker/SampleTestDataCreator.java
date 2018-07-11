package com.goaltracker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.goaltracker.dto.GoalDto;
import com.goaltracker.entity.Goal;
import com.goaltracker.entity.GoalBox;
import com.goaltracker.entity.GoalInBox;
import com.goaltracker.entity.TimeFrame;
import com.goaltracker.entity.Status;

public class SampleTestDataCreator {

	public static GoalDto createGoalDto() {
		GoalDto goal = new GoalDto();
		goal.setName("Sample Goal Dto");
		goal.setScope(TimeFrame.DAILY);
		goal.setStatus(Status.NOT_DONE);
		
		return goal;
	}
	
	public static Goal createGoal() {
		Goal goal = new Goal();
		goal.setCreatedAt(LocalDate.now());
		goal.setName("Sample Goal");
		goal.setStartedAt(LocalDate.now());
		goal.setStatus(Status.NOT_DONE);
		
		return goal;
	}
	
	public static GoalInBox createGoalInBox() {
		GoalInBox goal = new GoalInBox();
		goal.setCreatedAt(LocalDate.now());
		goal.setName("Sample Goal");
		goal.setStartedAt(LocalDate.now());
		goal.setStatus(Status.NOT_DONE);
		
		return goal;
	}
	
	public static GoalBox createGoalContainer() {
		GoalBox container = new GoalBox();
		container.setStarted(LocalDate.now());
		container.setScope(TimeFrame.DAILY);
		
		List<GoalInBox> goals = new ArrayList<>();
		GoalInBox goal = createGoalInBox();
		goals.add(goal);
		goal = createGoalInBox();
		goal.setName("SecondSampleGoal");
		goals.add(goal);
		
		container.setGoals(goals);
		
		return container;
	}
}
