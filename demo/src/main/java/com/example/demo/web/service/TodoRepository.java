package com.example.demo.web.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.web.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer> {

	List<Todo> findByUser1(String user1);
}
