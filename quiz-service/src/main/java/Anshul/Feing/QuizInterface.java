package Anshul.Feing;

import Anshul.model.QuestionWrapper;
import Anshul.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {

	// Generate random question IDs for a quiz
	@GetMapping("/question/generate/{category}/{numQuestions}")
	ResponseEntity<List<Integer>> getQuestionsForQuiz(
			@PathVariable("category") String category,
			@PathVariable("numQuestions") int numQuestions);

	// Get questions by their IDs
	@PostMapping("/question/getQuestions")
	ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(
			@RequestBody List<Integer> questionIds);

	// Calculate score based on responses
	@PostMapping("/question/getScore")
	ResponseEntity<Integer> getScore(
			@RequestBody List<Response> responses);
}
