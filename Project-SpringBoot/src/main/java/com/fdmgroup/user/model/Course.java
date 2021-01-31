package com.fdmgroup.user.model;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "user_course")
public class Course {

	@Id
	@GeneratedValue(generator = "user_coure_seq_gen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "user_course_seq_gen", sequenceName = "user_course_pk_seq", initialValue = 10)
	@Column(name = "course_id")
	private int courseId;
	
	@Column(name = "course_name")
	private String name;
	
	@Column(name = "duration")
	private int duration;
	
	@JsonBackReference
	@OneToMany(mappedBy = "course")
	private Set<Student> students;

	public Course(int courseId, String name, int duration, Set<Student> students) {
		super();
		this.courseId = courseId;
		this.name = name;
		this.duration = duration;
		this.students = students;
	}

	public Course() {
		super();
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	@Override
	public String toString() {
		return "Course [courseId=" + courseId + ", name=" + name + ", duration=" + duration +",  students=" 
				+ students.stream().map(Student::getName).collect(Collectors.toList()) + "]";
	}
	
	
	
	
	
}
