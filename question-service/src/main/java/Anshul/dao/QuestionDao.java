package Anshul.dao;

import Anshul.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {

	@Query(value = "SELECT q.id FROM question q WHERE q.category = :category ORDER BY RAND() LIMIT :num", nativeQuery = true)
	List<Integer> findQuestionsByCategory(String category, int num);

	List<Question> findByCategory(String category);
}
