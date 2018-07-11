package com.goaltracker.entity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class GoalBox {

	@Id
	private String id;
	private TimeFrame scope;
	
//	@DBRef
	private List<GoalInBox> goals;
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
	public List<GoalInBox> getGoals() {
		return goals;
	}
	public void setGoals(List<GoalInBox> goals) {
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
	
	public void addGoal(GoalInBox goal) {
		this.goals.add(goal);
	}
	
	public void removeGoal(GoalInBox goal) {
		this.goals.remove(goal);
		
	}
	
}
