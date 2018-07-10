package com.goaltracker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.goaltracker.entity.Goal;

@Repository
public interface GoalRepository extends MongoRepository<Goal, String>{
	
	Goal findByName(String name);

}
