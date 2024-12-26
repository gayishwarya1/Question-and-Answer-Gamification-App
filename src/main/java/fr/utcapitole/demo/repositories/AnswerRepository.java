package fr.utcapitole.demo.repositories;

import fr.utcapitole.demo.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    List<Answer> findAllByQuestionId(Integer questionId);

    List<Answer> findAllByAuthor(String author);
}
