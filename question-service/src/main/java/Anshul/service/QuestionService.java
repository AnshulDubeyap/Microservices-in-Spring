package Anshul.service;

import Anshul.dao.QuestionDao;
import Anshul.model.Question;
import Anshul.model.QuestionWrapper;
import Anshul.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

	@Autowired
	QuestionDao questionDao;

	// get all questions
	public ResponseEntity<List<Question>> getAllQuestions() {
		try{
			return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	// add a question
	public ResponseEntity<String> addQuestion(Question question) {

		// save the question
		questionDao.save(question);

		// return success message
		return new ResponseEntity<>("Question added successfully", HttpStatus.OK);
	}

	// get questions by category
	public ResponseEntity<List<Question>> getQuestionsByCategory( String category) {
		try{
			return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, int numQuestions) {

		List<Integer> questions = questionDao.findQuestionsByCategory(category, numQuestions);
		return new ResponseEntity<>(questions, HttpStatus.OK);

	}

	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
		List<QuestionWrapper> questionWrappers = new ArrayList<>();
		List<Question> questions = new ArrayList<>();

		for(Integer Id : questionIds) {
			if (questionDao.findById(Id).isPresent()) {
				questions.add(questionDao.findById(Id).get());
			}
		}

		for(Question question : questions) {
			QuestionWrapper questionWrapper = new QuestionWrapper();
			questionWrapper.setId(question.getId());
			questionWrapper.setQuestionTitle(question.getQuestionTitle());
			questionWrapper.setOption1(question.getOption1());
			questionWrapper.setOption2(question.getOption2());
			questionWrapper.setOption3(question.getOption3());
			questionWrapper.setOption4(question.getOption4());
			questionWrappers.add(questionWrapper);
		}
		return new ResponseEntity<>(questionWrappers, HttpStatus.OK);
	}

	public ResponseEntity<Integer> getScore(List<Response> responses) {
		int score = 0;
		for(Response response : responses) {
			if (questionDao.findById(response.getId()).isPresent()) {
				Question question = questionDao.findById(response.getId()).get();
				if(response.getResponse().equals(question.getRightAnswer())) {
					score++;
				}
			}
		}
		return new ResponseEntity<>(score, HttpStatus.OK);
	}

	public ResponseEntity<String> addQuestions(List<Question> questions) {
		questionDao.saveAll(questions);
		return new ResponseEntity<>("Questions added successfully", HttpStatus.OK);
	}
}