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

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}


	// add a question
	public ResponseEntity<String> addQuestion(Question question) {

		// save the question
		questionDao.save(question);

		// return success message
		return new ResponseEntity<>("Question added successfully", HttpStatus.OK);
	}

	// get questions by category
	public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
		try{
			return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, int numQuestions) {

		List<Integer> questions = questionDao.findQuestionsByCategory(categoryName, numQuestions);

		return new ResponseEntity<>(questions, HttpStatus.OK);

	}


	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {

		List<QuestionWrapper> wrappers = new ArrayList<>();

		List<Question> questions = new ArrayList<>();

		// fetch the questions
		for(Integer id : questionIds) {
			questions.add(questionDao.findById(id).get());
		}

		// remove the answers from the questions
		for(Question question : questions) {
			QuestionWrapper wrapper = new QuestionWrapper();
			wrapper.setId(question.getId());
			wrapper.setOption1(question.getOption1());
			wrapper.setOption2(question.getOption2());
			wrapper.setOption3(question.getOption3());
			wrapper.setOption4(question.getOption4());
			wrappers.add(wrapper);
		}

		return new ResponseEntity<>(wrappers, HttpStatus.OK);

	}

	public ResponseEntity<Integer> getScore(List<Response> responses) {

		int right = 0;

		for(Response response : responses){
			// get the question
			Question question = questionDao.findById(response.getId()).get();

			// check if the answer is correct
			if(response.getResponse().equals(question.getRightAnswer())) {
				right++;
			}

		}

		return new ResponseEntity<>(right, HttpStatus.OK);
	}
}
