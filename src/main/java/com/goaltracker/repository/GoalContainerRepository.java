package com.goaltracker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.goaltracker.entity.GoalContainer;

@Repository
public interface GoalContainerRepository extends MongoRepository<GoalContainer, String> {

}
