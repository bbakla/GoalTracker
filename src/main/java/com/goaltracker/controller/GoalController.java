package com.goaltracker.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.goaltracker.controller.resource.GoalResource;
import com.goaltracker.dto.GoalDto;
import com.goaltracker.service.DuplicateNameException;
import com.goaltracker.service.GoalService;
import com.goaltracker.service.ResourceNotFoundException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(value= Constants.GOAL_CONTROLLER_PATH)
public class GoalController {

	@Autowired
	private GoalService goalService; 
	
	@PostMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	@ApiOperation(value="create a goal", httpMethod="POST")
	public ResponseEntity<GoalResource> createGoal
		(@ApiParam(required=true) @RequestBody @Valid GoalDto goalDto) throws DuplicateNameException {
		
		GoalResource goalResource = goalService.saveGoal(goalDto);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.buildAndExpand(goalResource).toUri();
		
		return ResponseEntity
				.created(location)
				.body(goalResource);
	}
	
	@PutMapping(value="/{id}")
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="update a goal", httpMethod="PUT")
	public ResponseEntity<GoalResource> updateGoal(
			@ApiParam(required=true) @PathVariable String id, 
			@ApiParam @RequestBody @Valid GoalDto goalDto) throws ResourceNotFoundException {
		
		GoalResource updatedResource = goalService.updateGoal(id, goalDto);
		
		return ResponseEntity
				.ok()
				.body(updatedResource);
	}
	
	
	@GetMapping
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="get goals", httpMethod="GET")
	public ResponseEntity<List<GoalResource>> getGoals() {
		
		List<GoalResource> goals = goalService.getAllGoals();
		
		return ResponseEntity
				.ok()
				.body(goals);
	}
	
	@GetMapping(value="/{id}")
	@ResponseStatus(code=HttpStatus.OK)
	@ApiOperation(value="get goal", httpMethod="GET")
	public ResponseEntity<GoalResource> getGoal(@ApiParam @PathVariable String id, 
												@ApiParam @RequestBody @Valid GoalDto goalDto) throws ResourceNotFoundException{
		
		GoalResource resource = goalService.getGoalById(id);
		
		return ResponseEntity
				.ok()
				.body(resource);
	}
	
	@DeleteMapping(value="/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> deleteGoal(@ApiParam  @PathVariable String id) {
		goalService.deleteGoal(id);
		
		return ResponseEntity
				.noContent()
				.build();
	}
}
