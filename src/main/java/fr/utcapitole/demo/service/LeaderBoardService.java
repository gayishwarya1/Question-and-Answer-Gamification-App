package fr.utcapitole.demo.service;

import fr.utcapitole.demo.dto.LeaderBoard;
import fr.utcapitole.demo.entities.Answer;
import fr.utcapitole.demo.entities.User;
import fr.utcapitole.demo.repositories.AnswerRepository;
import fr.utcapitole.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class LeaderBoardService {

    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;

    public LeaderBoardService(UserRepository userRepository, AnswerRepository answerRepository) {
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
    }

    public List<LeaderBoard> getLeaderBoard() {
        List<User> users = userRepository.findAll();
        List<LeaderBoard> leaderBoards = new ArrayList<>();

        for (User user : users) {
            var allAnswers = answerRepository.findAllByAuthor(user.getUsername());
            int score = calculateScore(allAnswers);
            var leaderBoard = new LeaderBoard(user.getUsername(), score, user.getUsername(), user.getTeam().getName());
            leaderBoards.add(leaderBoard);
        }
        leaderBoards.sort(Comparator.comparing(LeaderBoard::getScore).reversed());
        return leaderBoards;
    }

    public int calculateScore(List<Answer> allAnswers) {
        int score = 0;
        for (Answer answer : allAnswers) {
            var likes = answer.getLikes();
            score += answer.isPinned() ? (likes > 0 ? likes * 7 : 7) : likes;
        }
        return score;
    }
}
