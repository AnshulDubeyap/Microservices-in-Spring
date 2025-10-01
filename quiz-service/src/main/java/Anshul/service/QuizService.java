package Anshul.service;

import Anshul.Feing.QuizInterface;
import Anshul.dao.QuizDao;
import Anshul.model.QuestionWrapper;
import Anshul.model.Quiz;
import Anshul.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

	@Autowired
	QuizDao quizDao;

	@Autowired
	QuizInterface quizInterface;

	public ResponseEntity<String> createQuiz(String category, int numQuestions, String title) {
		try {
			ResponseEntity<List<Integer>> response = quizInterface.getQuestionsForQuiz(category, numQuestions);
			if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
				List<Integer> questions = response.getBody();

				Quiz quiz = new Quiz();
				quiz.setTitle(title);
				quiz.setQuestionsIds(questions);
				quizDao.save(quiz);

				return new ResponseEntity<>("Quiz created successfully", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Failed to fetch questions", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error creating quiz: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(String id){
		try {
			Quiz quiz = quizDao.findById(id).orElse(null);
			if (quiz == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			List<Integer> questions = quiz.getQuestionsIds();

			ResponseEntity<List<QuestionWrapper>> questionWrapper = quizInterface.getQuestionsFromId(questions);

			return questionWrapper;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Integer> calculateResult(String id, List<Response> responses){
		try {
			Quiz quiz = quizDao.findById(id).orElse(null);
			if (quiz == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			// Optionally validate responses match quiz questions here

			ResponseEntity<Integer> score = quizInterface.getScore(responses);

			return score;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}