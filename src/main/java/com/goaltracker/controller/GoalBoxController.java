package com.goaltracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.goaltracker.controller.resource.GoalBoxResource;
import com.goaltracker.controller.resource.GoalResource;
import com.goaltracker.dto.GoalBoxDto;
import com.goaltracker.dto.GoalDto;
import com.goaltracker.service.GoalBoxService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import static com.goaltracker.controller.Constants.*;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping(value=GOAL_BOX_CONTROLLER_PATH)
public class GoalBoxController {
	
	@Autowired
	private GoalBoxService goalBoxService;
	
	@PostMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	@ApiOperation(value="Create a goalbox", httpMethod="POST")
	public ResponseEntity<GoalBoxResource> createGoalBox(@ApiParam(required=true) @RequestBody @Valid  GoalBoxDto goalBox) {
		
		GoalBoxResource createdGoalBox = goalBoxService.createGoalBox(goalBox);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.buildAndExpand(createdGoalBox).toUri();
		
		return ResponseEntity
				.created(location)
				.body(createdGoalBox);
	}
	
	@GetMapping
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Get all boxes", httpMethod="GET")
	public ResponseEntity<List<GoalBoxResource>> getAllGoalBoxes() {
		
		List<GoalBoxResource> goalBoxes = goalBoxService.getGoalBoxes();
		
		return ResponseEntity
				.ok()
				.body(goalBoxes);
	}
	
	@GetMapping(value="/{id}")
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Get a box", httpMethod="GET")
	public ResponseEntity<GoalBoxResource> getAGoalBox(@ApiParam(required=true) @PathVariable String id) {
		
		GoalBoxResource goalBoxResource = goalBoxService.getAGoalBox(id);
		
		return ResponseEntity
				.ok()
				.body(goalBoxResource);
	}
	
	@DeleteMapping(value="/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	@ApiOperation(value="Delete a goal box", httpMethod="DELETE")
	public ResponseEntity<Void> deleteGoalBox(@ApiParam(required=true) @PathVariable String id) {
		goalBoxService.deleteGoalBox(id);
		
		return ResponseEntity
				.noContent()
				.build();
		
	}
	
	@PutMapping(value="/{id}")
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Update a goal box", httpMethod="PUT")
	public ResponseEntity<GoalBoxResource> updateAGoalBox(
			@ApiParam(required= true) @RequestBody @Valid GoalBoxDto goalBoxDto,
			@ApiParam(required=true) @PathVariable String id) {
		
		GoalBoxResource updatedGoal = goalBoxService.updateGoal(goalBoxDto, id);
		
		return ResponseEntity
				.ok()
				.body(updatedGoal);
	}
	
	@PatchMapping(value="/{id}/goals/{goalName}")
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="Update a goal in the goalBox", httpMethod="PATCH")
	public ResponseEntity<GoalResource> updateAGoalInGoalBox(
			@ApiParam(required=true) @RequestBody @Valid GoalDto goalDto,
			@ApiParam(required=true) @PathVariable String id,
			@ApiParam(required=true) @PathVariable String goalName) {
		
		GoalResource goalResource = goalBoxService.updateAGoalInAGoalBox(goalDto, id, goalName);
		
		return ResponseEntity
				.ok()
				.body(goalResource);
	}

}
