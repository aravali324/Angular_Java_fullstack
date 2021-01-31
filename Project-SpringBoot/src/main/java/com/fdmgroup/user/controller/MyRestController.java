package com.fdmgroup.user.controller;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.user.dao.CourseDao;
import com.fdmgroup.user.dao.StudentDao;
import com.fdmgroup.user.model.Course;
import com.fdmgroup.user.model.Student;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class MyRestController {

	@Autowired
	StudentDao studentDao;
	@Autowired
	CourseDao courseDao;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam int id, @RequestParam String name,  HttpSession sessions){
		Optional<Student> foundstudent = studentDao.findByName(name);
		if (foundstudent.isPresent()) {
			if(sessions.getAttribute("studentname") == null ) {
				sessions.setAttribute("studentname", foundstudent.get().getName());
				sessions.setAttribute("studentid", foundstudent.get().getStudentId());
				return ResponseEntity.ok("You are logged In : " + " "+ foundstudent.get().getName());
			}else {
				return new ResponseEntity<Object>("Another user is already logged in", HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<Object>("There is no student with this id", HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpSession sessions){
		if(sessions.getAttribute("studentname") != null) {
			sessions.invalidate();
			return ResponseEntity.ok("Successful log out");
		}
		return new ResponseEntity<Object>("No user logged in", HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/addcourse")
	public ResponseEntity<?> postCourse(@RequestBody Course course) {
		courseDao.save(course);
		return new ResponseEntity<Object>("Course is added : "+ course.getName(), HttpStatus.OK);
	}

	@PostMapping("/addstudent")
	public ResponseEntity<?> postStudent(@RequestBody Student student) {
		studentDao.save(student);
		return new ResponseEntity<Object>("Student is added: " + student.getName(), HttpStatus.OK);
	}

	@GetMapping("/getstudents")
	public ResponseEntity<?> getStudents() {
		List<Student> allstudents = studentDao.findAll();
		if (allstudents.isEmpty()) {
			return new ResponseEntity<Object>("No students are present", HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(allstudents);
	}

	@GetMapping("/getcourses")
	public ResponseEntity<?> getCourses() {
		List<Course> allcourse = courseDao.findAll();
		if (allcourse.isEmpty()) {
			return new ResponseEntity<Object>("No courses are present", HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(allcourse);
	}

	@GetMapping("/students/{id}")
	public ResponseEntity<?> getStudent(@PathVariable int id) {
		Optional<Student> foundstudent = studentDao.findById(id);
		if (foundstudent.isPresent()) {
			return ResponseEntity.ok(foundstudent);
		}
		return new ResponseEntity<Object>("There is no student with this id", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/courses/{id}")
	public ResponseEntity<?> getCourse(@PathVariable int id) {
		Optional<Course> foundcourse = courseDao.findById(id);

		if (foundcourse.isPresent()) {
			return ResponseEntity.ok(foundcourse);
		}
		return new ResponseEntity<Object>("There is no course with this id", HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/student/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable int id) {
		Optional<Student> optionalStudent = studentDao.findById(id);
		if (optionalStudent.isPresent()) {
			studentDao.delete(optionalStudent.get());
			
//			return new ResponseEntity<>(HttpStatus.OK);
			return new ResponseEntity<Object>(
					"User has been deleted" + " " + "\n" + "student Name:" + " " + optionalStudent.get().getName()
							+ "\n" + "student Id:" + " " + optionalStudent.get().getStudentId(),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
	
	@PutMapping("/update/student/{id}")
	public ResponseEntity<?> updateStudent(@RequestBody Student student, @PathVariable int id){
		Optional<Student> foundstudent = studentDao.findById(id);
		
		if(foundstudent.isPresent()) {	
				List<Student> newstudent = foundstudent.stream().collect(Collectors.toList());
				Student newstudent1 = newstudent.get(0);
				newstudent1.setName(student.getName());
				studentDao.save(newstudent1);
				return new ResponseEntity<Object>("Updated studentName: "+ " "+ foundstudent.get().getName(), HttpStatus.OK);	
			}
			return new ResponseEntity<Object>("Enter valid ID", HttpStatus.NOT_FOUND);		

	}

	
	
//	@PutMapping("/update/student/{id}")
//	public ResponseEntity<?> updateStudent(@RequestBody Student student, @PathVariable int id, HttpSession request){
//		Optional<Student> foundstudent = studentDao.findById(id);
//		
//		if(foundstudent.isPresent()) {
//			if(foundstudent.get().getName().equals(request.getAttribute("studentname"))) {
//				
//				List<Student> newstudent = foundstudent.stream().collect(Collectors.toList());
//				System.out.println(newstudent.get(0).getName());
//				Student newstudent1 = newstudent.get(0);
//				newstudent1.setName(student.getName());
//				studentDao.save(newstudent1);
//				
//				System.out.println(request.getAttribute("studentname"));
//				System.out.println(request.getAttribute("studentid"));
//				
//				return new ResponseEntity<Object>("Updated studentName: "+ " "+ foundstudent.get().getName(), HttpStatus.OK);	
//			}
//			return new ResponseEntity<Object>("Enter valid ID", HttpStatus.NOT_FOUND);
//		}
//		
//		return new ResponseEntity<Object>("Enter valid ID" , HttpStatus.NOT_FOUND);
//
//	}

	
	
	@GetMapping("/course/students/{name}")
	public ResponseEntity<Object> getCourseStudents(@PathVariable String name) {
		Optional<Course> optionalCourse = courseDao.findByName(name);
		if (optionalCourse.isPresent()) {
			if(optionalCourse.get().getStudents().isEmpty()) {
				return new ResponseEntity<Object>("There is no students are enrolled in subject: "+ optionalCourse.get().getName(), HttpStatus.NOT_FOUND);
			}
			return ResponseEntity.ok(optionalCourse.get().getStudents());
		}
		return new ResponseEntity<Object>("No Such course available" , HttpStatus.NOT_FOUND);

	}
	
	@GetMapping("/search/student/findByNameStartingWith/{first}")
	public ResponseEntity<?> getByStartsname(@PathVariable String first){
		List<Student> optionalStudent = studentDao.findByNameStartingWith(first);
		if (optionalStudent.size() != 0) {
			return ResponseEntity.ok(optionalStudent);
		}
		return new ResponseEntity<Object>("There are no Students with name start with:" + first , HttpStatus.NOT_FOUND);
	}
	
	
	@GetMapping("/search/course/findByDurationLessThanAndNameStartingWith")
	public ResponseEntity<?> getByStartsnameandduration(@RequestParam String first, @RequestParam int duration){
		List<Course> optionalStudent = courseDao.findByDurationLessThanAndNameStartingWith(duration,first);
		System.out.println(optionalStudent);
		if (optionalStudent.size() != 0) {
			return ResponseEntity.ok(optionalStudent);
		}
		return new ResponseEntity<Object>("There are no course with name start with:" + first + " "+ " and duration less than: "+ duration , HttpStatus.NOT_FOUND);
	}
	

	@GetMapping("/search/findStudentByName")
	public ResponseEntity<?> getStudentWithName(@RequestParam String name) {
		Optional<Student> optionalStudent = studentDao.findByName(name);
		if (optionalStudent.isPresent()) {
			return ResponseEntity.ok(optionalStudent.get());
		}
		return new ResponseEntity<Object>("There is no Student with name:" + " " + name , HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/course/{id}/students")
	public ResponseEntity<Object> getCourseStudents(@PathVariable int id) {
		Optional<Course> optionalCourse = courseDao.findById(id);
		if (optionalCourse.isPresent()) {
			List<Course> newcourse = optionalCourse.stream().collect(Collectors.toList());
			if(newcourse.get(0).getStudents().isEmpty()) {
				return new ResponseEntity<Object>("There is no students are enrolled in subject: "+ optionalCourse.get().getName(), HttpStatus.NOT_FOUND);
			}
			return ResponseEntity.ok(optionalCourse.get().getStudents());
		}
		return new ResponseEntity<Object>("No students found" , HttpStatus.NOT_FOUND);

	}
	
}
