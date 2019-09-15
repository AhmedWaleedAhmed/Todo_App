package com.todo.rest.webservices.todo.rest.webservices.todo;

import java.net.URI;
import java.util.List;

import javax.validation.constraints.Positive;

import org.hibernate.annotations.UpdateTimestamp;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.todo.rest.webservices.todo.rest.webservices.todo.Todo;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TodoJPAResource {

	@Autowired
	private TodoHardcodedService todoService;
	
	@Autowired
	private TodoJpaRepository todoJpaRepository;
	
	@GetMapping(path = "/jpa/users/{username}/todos")
	public List<Todo> getAllTodos(@PathVariable String username){
		return todoJpaRepository.findByUsername(username);
//		return todoService.findAll();
	}
	
	
	@GetMapping(path = "/jpa/users/{username}/todos/{id}")
	public Todo getTodo(@PathVariable String username, @PathVariable long id){
		// it will return optional back but if you really want it use get
		return todoJpaRepository.findById(id).get();
//		return todoService.findById(id);
	}
	
	
	
	@DeleteMapping(path = "/jpa/users/{username}/todos/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable String username , @PathVariable long id)
	{
		
		//Todo todo = todoService.deleteById(id);
		
		todoJpaRepository.deleteById(id);
		return ResponseEntity.noContent().build();
		//return ResponseEntity.notFound().build();
		
	}
	
	@PutMapping(path = "/jpa/users/{username}/todos/{id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable String username 
			, @PathVariable long id , @RequestBody Todo todo)
	{
		Todo updatedTodo = todoJpaRepository.save(todo);
		return new ResponseEntity<Todo>(updatedTodo,HttpStatus.OK);
	}
	
	
	@PostMapping(path = "/jpa/users/{username}/todos")
	public ResponseEntity<Void> createTodo(@PathVariable String username , @RequestBody Todo todo)
	{
		todo.setUsername(username);
		Todo creatededTodo = todoJpaRepository.save(todo);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id").
		buildAndExpand(creatededTodo.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	
}
