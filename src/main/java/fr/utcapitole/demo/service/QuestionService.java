package fr.utcapitole.demo.service;

import fr.utcapitole.demo.dto.AnswerDto;
import fr.utcapitole.demo.entities.Answer;
import fr.utcapitole.demo.entities.Category;
import fr.utcapitole.demo.entities.Comment;
import fr.utcapitole.demo.entities.Question;
import fr.utcapitole.demo.repositories.AnswerRepository;
import fr.utcapitole.demo.repositories.CategoryRepository;
import fr.utcapitole.demo.repositories.CommentRepository;
import fr.utcapitole.demo.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CommentRepository commentRepository;

    public QuestionService(AnswerRepository answerRepository, QuestionRepository questionRepository,
                           CategoryRepository categoryRepository, CommentRepository commentRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.categoryRepository = categoryRepository;
        this.commentRepository = commentRepository;
    }

    public QuestionService() {}

    public Question createDraftQuestion() {
        var question = new Question();
        question.setCategory(new Category(""));
        return question;
    }

    public Question askQuestion(Question question, String principalName) {
        question.setAuthor(principalName);
        question.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        if ("".equals(question.getCategory().getId())) {
            question.setCategory(null);
        }
        return questionRepository.save(question);
    }

    public List<Question> getAllQuestionsByUser(String principalName) {
        return questionRepository.findByAuthor(principalName);
    }

    public List<Question> getAllQuestionsByCategory(String category) {
        var foundCategory = categoryRepository.findById(category)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return questionRepository.findByCategory(foundCategory);
    }

    public void answerQuestion(Integer questionId, String answerContent, String principalName) {
        var answer = new Answer();
        answer.setContent(answerContent);
        answer.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        answer.setAuthor(principalName);
        answer.setQuestionId(questionId);
        answerRepository.save(answer);
    }

    public List<AnswerDto> getAllAnswersByQuestionId(Integer questionId) {
        var answers = answerRepository.findAllByQuestionId(questionId);
        answers.sort(Comparator.comparing(Answer::getLikes).reversed());
        List<AnswerDto> answerList = new ArrayList<>();

        answers.forEach(answer -> {
            var comments = commentRepository.findByAnswerId(answer.getId());
            var answerDto = new AnswerDto(answer.getId(), answer.getContent(), answer.getAuthor(),
                    answer.getLikes(), answer.isPinned(), comments);
            if (answer.isPinned()) {
                answerList.add(0, answerDto);
            } else {
                answerList.add(answerDto);
            }
        });
        return answerList;
    }

    public void increaseLikesForAnswer(Integer answerId) {
        var answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        answer.setLikes(answer.getLikes() + 1);
        answerRepository.save(answer);
    }

    public void togglePinForAnswer(Integer answerId) {
        var answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        answer.setPinned(!answer.isPinned());
        answerRepository.save(answer);
    }

    public void addComment(Integer answerId, String authorName, String content) {
        Comment comment = new Comment();
        comment.setAnswerId(answerId);
        comment.setAuthor(authorName);
        comment.setContent(content);

        commentRepository.save(comment);
    }

}