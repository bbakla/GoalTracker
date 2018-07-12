package com.goaltracker.dto;

import java.time.LocalDate;
import java.util.List;

import com.goaltracker.entity.TimeFrame;

public class GoalBoxDto {

	private TimeFrame scope;
	private List<GoalDto> goals;
	private LocalDate started;
	private LocalDate finished;
	
	public TimeFrame getScope() {
		return scope;
	}
	public void setScope(TimeFrame scope) {
		this.scope = scope;
	}
	public List<GoalDto> getGoals() {
		return goals;
	}
	public void setGoals(List<GoalDto> goals) {
		this.goals = goals;
	}
	public LocalDate getStarted() {
		return started;
	}
	public void setStarted(LocalDate started) {
		this.started = started;
	}
	public LocalDate getFinished() {
		return finished;
	}
	public void setFinished(LocalDate finished) {
		this.finished = finished;
	}
	
	
}
