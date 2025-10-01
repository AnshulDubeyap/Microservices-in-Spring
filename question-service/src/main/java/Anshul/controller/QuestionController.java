package Anshul.controller;

import Anshul.model.Question;
import Anshul.model.QuestionWrapper;
import Anshul.model.Response;
import Anshul.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	QuestionService questionService;

	// get all questions
	@GetMapping("allQuestions")
	public ResponseEntity<List<Question>> getAllQuestions() {
		return questionService.getAllQuestions();
	}

	// get questions by category
	@GetMapping("category/{category}")
	public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
		return questionService.getQuestionsByCategory(category);
	}

	// add a question
	@PostMapping("add")
	public ResponseEntity<String> addQuestion(@RequestBody Question question) {
		return questionService.addQuestion(question);
	}

	// add List of questions
	@PostMapping("addList")
	public ResponseEntity<String> addQuestions(@RequestBody List<Question> questions) {
		return questionService.addQuestions(questions);
	}

	//===================For Quiz Service===================//
	// to make this microservice independent, we need to create three new endpoints
	// 1. generate the random questions
	// 2. get question by id
	// 3. get score

	// generate the random questions
	@GetMapping("/generate/{category}/{numQuestions}")
	public ResponseEntity<List<Integer>> getQuestionsForQuiz(
			@PathVariable("category") String category,
			@PathVariable("numQuestions") int numQuestions) {
		return questionService.getQuestionsForQuiz(category, numQuestions);
	}

	// get question by id
	@PostMapping("getQuestions")
	public ResponseEntity<List<QuestionWrapper>> getQuestionsById(@RequestBody List<Integer> questionIds){
		return questionService.getQuestionsFromId(questionIds);
	}

	// get score
	@PostMapping("getScore")
	public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
		return questionService.getScore(responses);
	}

}