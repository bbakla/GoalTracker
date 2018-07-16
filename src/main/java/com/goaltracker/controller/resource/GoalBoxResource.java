package com.goaltracker.controller.resource;

import java.time.LocalDate;
import java.util.List;

import com.goaltracker.entity.Goal;
import com.goaltracker.entity.TimeFrame;

public class GoalBoxResource {

	private String id;
	private TimeFrame scope;
	private List<Goal> goals;
	private LocalDate started;
	private LocalDate finished;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public TimeFrame getScope() {
		return scope;
	}
	public void setScope(TimeFrame scope) {
		this.scope = scope;
	}
	public List<Goal> getGoals() {
		return goals;
	}
	public void setGoals(List<Goal> goals) {
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
