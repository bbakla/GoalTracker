package com.goaltracker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.goaltracker.entity.GoalBox;

@Repository
public interface GoalBoxRepository extends MongoRepository<GoalBox, String> {

}
