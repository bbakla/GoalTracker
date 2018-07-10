package com.goaltracker.controller.resource;

import com.goaltracker.entity.Status;
import com.goaltracker.entity.TimeFrame;

public class GoalResource {
	
	private String id;
	private String name;
	private TimeFrame scope;
	private Status status;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
