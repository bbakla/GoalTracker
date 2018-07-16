package com.goaltracker.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goaltracker.controller.resource.GoalBoxResource;
import com.goaltracker.controller.resource.GoalResource;
import com.goaltracker.dto.GoalBoxDto;
import com.goaltracker.dto.GoalDto;
import com.goaltracker.entity.Goal;
import com.goaltracker.entity.GoalBox;
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
		
		return  convertGoalBoxToGoalBoxResource(savedGoalBox);
	}
	
	public List<GoalBoxResource> getGoalBoxes() {
		List<GoalBox> goalBoxes = goalBoxRepository.findAll();
		
		List<GoalBoxResource> goalBoxResources = new ArrayList<>();
		
		goalBoxes.forEach(goalBox -> {
			GoalBoxResource resource = convertGoalBoxToGoalBoxResource(goalBox);
			goalBoxResources.add(resource);
		});
		
		return goalBoxResources;
	}
	
	public GoalBoxResource getAGoalBox(String id) {
		GoalBox goalBox = goalBoxRepository.findById(id).get();
		
		return convertGoalBoxToGoalBoxResource(goalBox);
	}
	
	private GoalBox convertGoalBoxDtoToGoalBox(GoalBoxDto goalBoxDto) {
		GoalBox goalBox = mapper.map(goalBoxDto, GoalBox.class);
		
		List<Goal> goalsInBox = new ArrayList<>();
		
		goalBoxDto.getGoals().forEach(goal -> {
			Goal goalInBox = mapper.map(goal, Goal.class);
			goalsInBox.add(goalInBox);
		});
		
		return goalBox;
	}
	
	private GoalBoxResource convertGoalBoxToGoalBoxResource(GoalBox goalBox) {
		GoalBoxResource goalBoxResource = mapper.map(goalBox, GoalBoxResource.class);
		
		List<GoalResource> goalsInBox = new ArrayList<>();
		
		goalBox.getGoals().forEach(goal -> {
			GoalResource goalInBoxResource = mapper.map(goal, GoalResource.class);
			goalsInBox.add(goalInBoxResource);
		});
		
		return goalBoxResource;
	}

	public void deleteGoalBox(String id) {
		goalBoxRepository.deleteById(id);
	}

	public GoalBoxResource updateGoal(GoalBoxDto newGoalBoxDto, String id) {
		GoalBox newGoalBox = mapper.map(newGoalBoxDto, GoalBox.class);
		
		GoalBox inRepo = goalBoxRepository.findById(id).get();
		
		newGoalBox.setId(id);
		
		GoalBox updatedGoalBox = goalBoxRepository.save(newGoalBox);
		
		return convertGoalBoxToGoalBoxResource(updatedGoalBox);
	}

	public GoalResource updateAGoalInAGoalBox(GoalDto goalDto, String id, String goalName) {
		GoalBox goalBox = goalBoxRepository.findById(id).get();
		
		goalBox.getGoals().forEach(goal -> {
			if(goal.getName().equals(goalName)) {
				
//				GoalInBox updatedGoal = new GoalInBox();
				goal.setName(goalDto.getName());
				goal.setStatus(goalDto.getStatus());
				
//				updatedGoal.setCreatedAt(goal.getCreatedAt());
//				updatedGoal.setStartedAt(goal.getStartedAt());
//				goalBox.getGoals().remove(goal);
//				goalBox.getGoals().add(updatedGoal);
			}
		});
		
		GoalBox updatedGoalBox = goalBoxRepository.save(goalBox);
		
		Goal updatedGoalInBox = updatedGoalBox.getGoals()
						.stream()
						.filter(goal -> goal.getName().equals(goalName))
						.findAny()
						.get();
		
		
		return mapper.map(updatedGoalInBox, GoalResource.class) ;
	}
}
