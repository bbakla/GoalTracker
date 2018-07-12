package com.goaltracker.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goaltracker.controller.resource.GoalBoxResource;
import com.goaltracker.controller.resource.GoalInBoxResource;
import com.goaltracker.dto.GoalBoxDto;
import com.goaltracker.entity.GoalBox;
import com.goaltracker.entity.GoalInBox;
import com.goaltracker.repository.GoalBoxRepository;
import com.goaltracker.repository.GoalRepository;

@Service
public class GoalBoxService {
	
	@Autowired
	private GoalBoxRepository goalBoxRepository;
	
	@Autowired
	private GoalRepository goalRepository;
	
	@Autowired
	private ModelMapper mapper;

	public GoalBoxResource createGoalBox(@Valid GoalBoxDto goalBoxDto) {
		GoalBox convertedGoalBox = convertGoalBoxDtoToGoalBox(goalBoxDto);
		
		GoalBox savedGoalBox = goalBoxRepository.save(convertedGoalBox);
		GoalBoxResource goalBoxResource = convertGoalBoxToGoalBoxResource(savedGoalBox);
		
		return goalBoxResource;
	}
	
	private GoalBox convertGoalBoxDtoToGoalBox(GoalBoxDto goalBoxDto) {
		GoalBox goalBox = mapper.map(goalBoxDto, GoalBox.class);
		
		List<GoalInBox> goalsInBox = new ArrayList<>();
		
		goalBoxDto.getGoals().forEach(goal -> {
			GoalInBox goalInBox = mapper.map(goal, GoalInBox.class);
			goalsInBox.add(goalInBox);
		});
		
		return goalBox;
	}
	
	private GoalBoxResource convertGoalBoxToGoalBoxResource(GoalBox goalBox) {
		GoalBoxResource goalBoxResource = mapper.map(goalBox, GoalBoxResource.class);
		
		List<GoalInBoxResource> goalsInBox = new ArrayList<>();
		
		goalBox.getGoals().forEach(goal -> {
			GoalInBoxResource goalInBoxResource = mapper.map(goal, GoalInBoxResource.class);
			goalsInBox.add(goalInBoxResource);
		});
		
		return goalBoxResource;
	}
}
