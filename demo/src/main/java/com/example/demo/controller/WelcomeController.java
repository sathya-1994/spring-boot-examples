package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.web.model.Todo;
import com.example.demo.web.service.TodoRepository;

@Controller
public class WelcomeController {

//	@RequestMapping("/login")
//	@ResponseBody
//	public String loginMessage(@RequestParam String name,@RequestParam int age, ModelMap model) {
//		//return "Hello World  Modified";
//		//System.out.println("Name is "+ name);
//		model.put("name", name);
//		model.put("age", age);
//		System.out.println(age);
//		return "login";
//	}

//	@RequestMapping("/login")
//	public int ageMessage(@RequestParam int age,ModelMap model) {
//		System.out.println("Age is "+ age);
//		model.put("age", age);
//		return age;
//	}

	@Autowired
	TodoRepository repository;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// Date - dd/MM/yyyy
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showWelcomePage(ModelMap model) {
		model.put("name", getLoogedInUserName());

		return "welcome";
	}

	@RequestMapping(value = "/list-todos", method = RequestMethod.GET)
	public String showTodoPage(ModelMap model) {
		String name = getUserName();
		// model.put("todos", service.retrieveTodos(name));
		model.put("todos", repository.findByUser(name));
		return "list-todos";
	}

	private String getUserName() {
		return getLoogedInUserName();
	}

	@RequestMapping(value = "/delete-todo", method = RequestMethod.GET)
	public String deleteTodo(@RequestParam int id) {
//		if(id ==1) {
//			throw new RuntimeException("Something went wrong");
//		}
		repository.deleteById(id);
		return "redirect:/list-todos";
	}

	@RequestMapping(value = "/update-todo", method = RequestMethod.GET)
	public String updateTodo(@RequestParam int id, ModelMap model) {
		Optional<Todo> todo = repository.findById(id);
		// Negative scenario not handled
		model.put("todo", todo.get());
		return "todo";
	}

	@RequestMapping(value = "/update-todo", method = RequestMethod.POST)
	public String showUpdateTodoPage(ModelMap model, @Valid Todo todo, BindingResult result) {
		todo.setUser(getUserName());
		if (result.hasErrors()) {
			return "todo";
		}
		repository.save(todo);
		return "redirect:/list-todos";
	}

	@RequestMapping(value = "/add-todo", method = RequestMethod.GET)
	public String showAddTodoPage(ModelMap model) {
		// service.addTodo((String) model.get("name"), desc, new Date(), false);

		model.addAttribute("todo", new Todo(0, getUserName(), "Default Desc", new Date(), false));
		return "todo";
	}

	@RequestMapping(value = "/add-todo", method = RequestMethod.POST)
	public String addTodoPage(ModelMap model, @Valid Todo todo, BindingResult result) {
		if (result.hasErrors()) {
			return "todo";
		}
		todo.setUser(getUserName());
		repository.save(todo);
		// service.addTodo(getUserName(), todo.getDesc(), new Date(), false);
		// model.put("todos", service.retrieveTodos((String) model.get("name")));
		return "redirect:/list-todos";
	}

	private String getLoogedInUserName() {
		Authentication principal = SecurityContextHolder.getContext().getAuthentication();
		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}
		return principal.getName();
	}
}
