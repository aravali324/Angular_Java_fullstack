package com.fdmgroup.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "user_student")
public class Student {

	@Id
	@GeneratedValue(generator = "user_student_seq_gen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "user_student_seq_gen", sequenceName = "user_student_pk_seq", initialValue = 11)
	@Column(name = "student_id")
	private int studentId;
	
	
	@Column(name = "student_name")
	private String name; 
	
	@ManyToOne
	@JoinColumn(name= "course_id")
	private Course course;

	public Student() {
		super();
	}

	public Student(int studentId, String name, Course course) {
		super();
		this.studentId = studentId;
		this.name = name;
		this.course = course;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", name=" + name + ", course=" + course + "]";
	}
	
	
	
}
