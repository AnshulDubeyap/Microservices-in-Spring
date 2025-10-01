package Anshul.controller;

import Anshul.model.QuestionWrapper;
import Anshul.model.QuizDto;
import Anshul.model.Response;
import Anshul.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

	@Autowired
	QuizService quizService;


	// create Quiz
	@PostMapping("create")
	public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto) {
		return quizService.createQuiz(quizDto.getCategory(), quizDto.getNumQuestions(), quizDto.getTitle());
	}

	// get quiz questions
	@GetMapping("get/{id}")
	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable String id) {
		return quizService.getQuizQuestions(id);
	}

	// get score
	@PostMapping("getScore/{id}")
	public ResponseEntity<Integer> calculateResult(@PathVariable String id, @RequestBody List<Response> responses) {
		return quizService.calculateResult(id, responses);

	}


}
