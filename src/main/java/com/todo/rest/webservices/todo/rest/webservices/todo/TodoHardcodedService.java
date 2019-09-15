package com.todo.rest.webservices.todo.rest.webservices.todo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;


@Service
public class TodoHardcodedService {

	//this will act for now as our database 
	private static List<Todo> todos =new ArrayList(); 
	
	private static long idCounter = 0;
	
	static {
		
		todos.add(new Todo (++idCounter,"Ahmed" , "learn german" ,new Date(),false));	
		todos.add(new Todo (++idCounter,"Ahmed" , "learn French" ,new Date(),false));	
		todos.add(new Todo (++idCounter,"Ahmed" , "learn English" ,new Date(),false));	
	}
	
	public List<Todo> findAll(){
		return todos;
	}
	
	public Todo deleteById(long id)
	{
		Todo todo = findById(id);
		if (todo != null) {
			if (todos.remove(todo))
			{
				return todo;
			}
		}
		return null;
	}

	public Todo findById(long id) {
		for (Todo todo : todos)
		{
			if (todo.getId()==id)
				return todo;
		}
		return null;
	}
	
	public Todo save (Todo todo)
	{
		if (todo.getId() == -1 || todo.getId() == 0)
		{
			todo.setId(++idCounter);
			todos.add(todo);
		}
		else
		{
			this.deleteById(todo.getId());
			todos.add(todo);
		}
		return todo;
	}
}
