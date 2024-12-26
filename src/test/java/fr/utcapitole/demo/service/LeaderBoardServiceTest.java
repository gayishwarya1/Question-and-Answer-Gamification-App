package fr.utcapitole.demo.service;

import fr.utcapitole.demo.entities.Answer;
import fr.utcapitole.demo.repositories.AnswerRepository;
import fr.utcapitole.demo.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class LeaderBoardServiceTest {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private UserRepository userRepository;

    private LeaderBoardService leaderBoardService;

    @BeforeEach
    public void beforeEach() {
        leaderBoardService = new LeaderBoardService(userRepository, answerRepository);
    }

    @Test
    void testCalculateScore() {
        List<Answer> answers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            var answer = new Answer();
            answer.setLikes(1);
            answers.add(answer);
        }
        var score = leaderBoardService.calculateScore(answers);
        var total = 3; // one like costs 1 point
        assertThat(score).isEqualTo(total);

        for (int i = 0; i < 3; i++) {
            var answer = new Answer();
            answer.setPinned(true);
            answers.add(answer);
        }
        total = 3 + 7 * 3; // one pin costs 7 + 3 from previous answers
        score = leaderBoardService.calculateScore(answers);
        assertThat(score).isEqualTo(total);

        for (int i = 0; i < 2; i++) {
            var answer = new Answer();
            answer.setPinned(true);
            answer.setLikes(2);
            answers.add(answer);
        }
        total = 3 + 7 * 3 + 2 * 2 * 7; // total likes + pins: we added 2 answers with 2 likes and one pin
        score = leaderBoardService.calculateScore(answers);
        assertThat(score).isEqualTo(total);
    }
}
