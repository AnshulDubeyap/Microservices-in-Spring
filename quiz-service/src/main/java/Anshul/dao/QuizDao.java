package Anshul.dao;

import Anshul.model.Quiz;
import org.hibernate.dialect.MySQLDialect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizDao extends JpaRepository<Quiz, String> {
}
