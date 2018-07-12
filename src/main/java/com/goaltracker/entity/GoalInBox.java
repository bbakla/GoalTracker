package com.goaltracker.entity;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="goals")
public class GoalInBox {

	@NotBlank
//	@Indexed(unique=true)
	private String name;
	private LocalDate startedAt;
	private LocalDate createdAt;
	private Status status;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getStartedAt() {
		return startedAt;
	}
	public void setStartedAt(LocalDate startedAt) {
		this.startedAt = startedAt;
	}
	public LocalDate getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
}
