package fr.utcapitole.demo.service;

import fr.utcapitole.demo.entities.Category;
import fr.utcapitole.demo.entities.Question;
import fr.utcapitole.demo.repositories.AnswerRepository;
import fr.utcapitole.demo.repositories.CategoryRepository;
import fr.utcapitole.demo.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public List<Question> getUnansweredQuestions() {
        var questions = questionRepository.findAll();
        List<Question> unansweredQuestions = new ArrayList<>();

        for (Question question : questions) {
            var answers = answerRepository.findAllByQuestionId(question.getId());
            if (answers.isEmpty()) {
                unansweredQuestions.add(question);
            }
        }
        return unansweredQuestions;
    }
}
