package com.goaltracker.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.goaltracker.controller.resource.GoalBoxResource;
import com.goaltracker.dto.GoalBoxDto;
import com.goaltracker.service.GoalBoxService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import static com.goaltracker.controller.Constants.*;

import java.net.URI;

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

}
