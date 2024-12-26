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
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnswerRepository answerRepository;

    private UserService userService;

    @BeforeEach
    private void beforeEach() {
        userService = new UserService(userRepository, new MailService(), answerRepository);
    }

    @Test
    void testGetBadge() {
        List<Answer> answers = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            var answer = new Answer();
            answers.add(answer);
        }
        var badge = userService.getBadge(answers);

        assertThat(badge).isNotNull();
        assertThat(badge).isEqualTo("Bronze");

        for (int i = 0; i < 3; i++) {
            var answer = new Answer();
            answer.setLikes(1);
            answers.add(answer);
        }
        badge = userService.getBadge(answers);

        assertThat(badge).isNotNull();
        assertThat(badge).isEqualTo("Silver");

        for (int i = 0; i < 3; i++) {
            var answer = new Answer();
            answer.setPinned(true);
            answers.add(answer);
        }
        badge = userService.getBadge(answers);

        assertThat(badge).isNotNull();
        assertThat(badge).isEqualTo("Gold");
    }
}
