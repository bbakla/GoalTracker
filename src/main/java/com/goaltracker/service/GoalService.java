package com.goaltracker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goaltracker.controller.resource.GoalResource;
import com.goaltracker.dto.GoalDto;
import com.goaltracker.entity.Goal;
import com.goaltracker.repository.GoalRepository;

@Service
public class GoalService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private GoalRepository goalRepository;

	public GoalResource saveGoal(GoalDto goalDto) throws DuplicateNameException {
		Goal goal = mapper.map(goalDto, Goal.class);

		if (goalRepository.findByName(goalDto.getName()) != null) {
			throw new DuplicateNameException();
		}

		Goal savedGoal = goalRepository.save(goal);

		return mapper.map(savedGoal, GoalResource.class);
	}

	public List<GoalResource> getAllGoals() {

		List<Goal> goals = goalRepository.findAll();

		List<GoalResource> goalResources = new ArrayList<>();

		goals.forEach(goal -> {
			GoalResource goalResource = mapper.map(goal, GoalResource.class);
			goalResources.add(goalResource);
		});

		return goalResources;
	}

	public GoalResource getGoalById(String id) throws ResourceNotFoundException {
		Optional<Goal> goal = goalRepository.findById(id);
		
		if(goal.isPresent()) {
			return mapper.map(goal.get(), GoalResource.class);
		} else {
			throw new ResourceNotFoundException();
		}
	}

	public GoalResource updateGoal(String id, GoalDto updatedGoalDto) throws ResourceNotFoundException  {
		Optional<Goal> goal = goalRepository.findById(id);
		
		if(goal.isPresent()) {
			Goal toBeUpdatedGoal = mapper.map(updatedGoalDto, Goal.class);
			toBeUpdatedGoal.setId(goal.get().getId());
			Goal updatedGoal = goalRepository.save(toBeUpdatedGoal);
			
			return mapper.map(updatedGoal, GoalResource.class);
		} else {
			throw new ResourceNotFoundException();
		}
	}

	/**
	 * 1- Goal should not be in a goalbox
	 * @param id
	 */
	public void deleteGoal(String id) {
		
		// TODO Auto-generated method stub
		
	}
}
