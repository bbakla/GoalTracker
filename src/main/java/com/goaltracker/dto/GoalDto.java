package com.goaltracker.dto;

import com.goaltracker.entity.TimeFrame;
import com.goaltracker.entity.Status;

public class GoalDto {

	private String name;
	private TimeFrame scope;
	private Status status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TimeFrame getScope() {
		return scope;
	}

	public void setScope(TimeFrame scope) {
		this.scope = scope;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
