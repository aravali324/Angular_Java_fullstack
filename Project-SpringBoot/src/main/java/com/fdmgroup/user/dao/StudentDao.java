package com.fdmgroup.user.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.user.model.Student;

public interface StudentDao extends JpaRepository<Student, Integer> {
	
	Optional<Student> findByName(String name);
	List<Student> findByNameStartingWith(String first);

	void save(Optional<Student> foundstudent);

}
