package com.goaltracker.dto;

import com.goaltracker.entity.Scope;
import com.goaltracker.entity.Status;

public class GoalDto {

	private String name;
	private Scope scope;
	private Status status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
