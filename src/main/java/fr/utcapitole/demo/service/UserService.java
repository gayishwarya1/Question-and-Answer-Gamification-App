package fr.utcapitole.demo.service;

import fr.utcapitole.demo.dto.UserDto;
import fr.utcapitole.demo.entities.Answer;
import fr.utcapitole.demo.repositories.AnswerRepository;
import fr.utcapitole.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private MailService mailService;
    private AnswerRepository answerRepository;

    public UserService(UserRepository userRepository, MailService mailService, AnswerRepository answerRepository) {
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.answerRepository = answerRepository;
    }

    public void lockAccount(String username) {
        userRepository.findById(username).ifPresent(user -> {
            if (user.isAccountNonLocked()) {
                user.setLocked(true);
                userRepository.saveAndFlush(user);
                mailService.sendEmail(user.getEmail(), "Sorry, your account has been locked");
            }
        });
    }

    public void unlockAccount(String username) {
        userRepository.findById(username).ifPresent(user -> {
            if (!user.isAccountNonLocked()) {
                user.setLocked(false);
                userRepository.saveAndFlush(user);
                mailService.sendEmail(user.getEmail(), "Good news, your account has been unlocked!");
            }
        });
    }

    public Optional<UserDto> getUserByUsername(String username) {
        var user = userRepository.findById(username);
        var answersForUse = answerRepository.findAllByAuthor(username);
        String badge = getBadge(answersForUse);

        if (user.isPresent()) {
            var userEntity = user.get();
            var bd = userEntity.getBirthday().isPresent() ? userEntity.getBirthday().get() : null;
            var userDto = new UserDto(userEntity.getUsername(), userEntity.getRoles(),
                    bd, userEntity.isAccountNonLocked(), userEntity.getEmail(),
                    userEntity.getScore(), badge, userEntity.getTeam().getName());
            return Optional.of(userDto);
        } else {
            return Optional.empty();

        }
    }

    public String getBadge(List<Answer> answers) {
        var badge = "";
        if (!answers.isEmpty()) {
            badge = "Bronze";
        }
        for (Answer answer : answers) {
            if (answer.getLikes() > 0) {
                badge = "Silver";
            }
            if (answer.isPinned()) {
                badge = "Gold";
                break;
            }
        }
        return badge;
    }
}
