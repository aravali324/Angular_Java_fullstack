package com.fdmgroup.user.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.user.model.Course;

public interface CourseDao extends JpaRepository<Course, Integer> {
	
	Optional<Course> findByName(String name);
	Optional<Course> findByDurationLessThan(int duration);
	List<Course> findByDurationLessThanAndNameStartingWith( int duration,String name);
}
