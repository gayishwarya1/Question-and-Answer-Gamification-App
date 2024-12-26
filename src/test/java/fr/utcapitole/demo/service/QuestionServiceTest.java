package fr.utcapitole.demo.service;

import fr.utcapitole.demo.repositories.AnswerRepository;
import fr.utcapitole.demo.repositories.CategoryRepository;
import fr.utcapitole.demo.repositories.CommentRepository;
import fr.utcapitole.demo.repositories.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class QuestionServiceTest {

    private QuestionService questionService;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    public void beforeEach() {
        questionService = new QuestionService(answerRepository, questionRepository, categoryRepository, commentRepository);
    }

    @Test
    void testCreateQuestion() {
        var question = questionService.createDraftQuestion();
        assertThat(question).isNotNull();
        assertThat(question.getCategory()).isNotNull();
        assertThat(question.getCategory().getId()).isEqualTo("");
    }

    @Test
    void testAskQuestion() {
        var question = questionService.createDraftQuestion();
        question = questionService.askQuestion(question, "Edward");
        assertThat(question).isNotNull();
        assertThat(question.getCategory()).isNull();
        assertThat(question.getAuthor()).isEqualTo("Edward");
    }

    @Test
    void testAnswerQuestion() {
        var question = questionService.createDraftQuestion();
        question = questionService.askQuestion(question, "George");

        questionService.answerQuestion(question.getId(), "Content", "Adelina");
        var answer = questionService.getAllAnswersByQuestionId(question.getId());
        assertThat(answer).isNotEmpty();
        assertThat(answer.get(0).getAuthorName()).isEqualTo("Adelina");
        assertThat(answer.get(0).getContent()).isEqualTo("Content");
    }

    @Test
    void testIncreaseLikesForAnswer() {
        var question = questionService.createDraftQuestion();
        question = questionService.askQuestion(question, "George");

        questionService.answerQuestion(question.getId(), "Content", "Adelina");
        var answers = questionService.getAllAnswersByQuestionId(question.getId());
        assertThat(answers).isNotEmpty();

        var answer = answers.get(0);
        assertThat(answer.getLikes()).isEqualTo(0);
        questionService.increaseLikesForAnswer(answer.getId());

        var answerEntity = answerRepository.findById(answer.getId());
        assertThat(answerEntity).isNotNull();
        assertThat(answerEntity.get().getLikes()).isEqualTo(1);
    }

    @Test
    void testTogglePinForAnswer() {
        var question = questionService.createDraftQuestion();
        question = questionService.askQuestion(question, "George");

        questionService.answerQuestion(question.getId(), "Content", "Adelina");
        var answers = questionService.getAllAnswersByQuestionId(question.getId());
        assertThat(answers).isNotEmpty();

        var answer = answers.get(0);
        assertThat(answer.isPinned()).isFalse();
        questionService.togglePinForAnswer(answer.getId());

        var answerEntity = answerRepository.findById(answer.getId());
        assertThat(answerEntity).isNotNull();
        assertThat(answerEntity.get().isPinned()).isTrue();
    }

    @Test
    void addComment() {
        var question = questionService.createDraftQuestion();
        question = questionService.askQuestion(question, "George");

        questionService.answerQuestion(question.getId(), "Content", "Adelina");
        var answers = questionService.getAllAnswersByQuestionId(question.getId());
        assertThat(answers).isNotEmpty();

        questionService.addComment(answers.get(0).getId(), "Comment person",
                "Content comment");

        var comments = commentRepository.findAll();
        assertThat(comments).isNotEmpty();
        assertThat(comments.get(0)).isNotNull();
        assertThat(comments.get(0).getAnswerId()).isEqualTo(answers.get(0).getId());
        assertThat(comments.get(0).getAuthor()).isEqualTo("Comment person");
    }

}
