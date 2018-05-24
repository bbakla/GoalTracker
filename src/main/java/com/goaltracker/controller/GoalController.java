package com.goaltracker.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.goaltracker.controller.dto.GoalDto;
import com.goaltracker.controller.resource.GoalResource;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class GoalController {

	@PostMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	public ResponseEntity<GoalResource> createGoal(@RequestBody @Valid GoalDto goalDto) {
		
		return null;
	}
}
