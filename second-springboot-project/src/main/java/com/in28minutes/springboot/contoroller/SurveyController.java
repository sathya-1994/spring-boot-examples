package com.in28minutes.springboot.contoroller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.springboot.model.Question;
import com.in28minutes.springboot.service.SurveyService;

@RestController
public class SurveyController {

	@Autowired
	private SurveyService surveyService;

	// Get "/surveys/{surveyId}/questions"
	@GetMapping("/surveys/{surveyId}/questions")
	public List<Question> retiveQuestionsForSurvey(@PathVariable String surveyId) {
		return surveyService.retrieveQuestions(surveyId);
	}

	// Post "/surveys/{surveyId}/questions"
	@PostMapping("/surveys/{surveyId}/questions")
	public ResponseEntity<Object> addQuestionToSurvey(@PathVariable String surveyId,
			@RequestBody Question newQuestion) {
		Question createdTodo = surveyService.addQuestion(surveyId, newQuestion);
		if (createdTodo == null)
			return ResponseEntity.noContent().build();
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdTodo.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	// Get "/surveys/{surveyId}/questions/{questionId}"
	@GetMapping("/surveys/{surveyId}/questions/{questionId}")
	public Question retiveQuestionsForQuestion(@PathVariable String surveyId, @PathVariable String questionId) {
		return surveyService.retrieveQuestion(surveyId, questionId);
	}
}
