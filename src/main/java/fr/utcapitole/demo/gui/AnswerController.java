package fr.utcapitole.demo.gui;

import fr.utcapitole.demo.entities.Question;
import fr.utcapitole.demo.repositories.CategoryRepository;
import fr.utcapitole.demo.repositories.QuestionRepository;
import fr.utcapitole.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
public class AnswerController {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionService questionService;

    @GetMapping(value = "/questions/ask")
    public String createDraftQuestion(final ModelMap model) {
        Question question = questionService.createDraftQuestion();
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("question", question);
        return "question-new";
    }

    @PostMapping(value = "/questions/ask")
    public String askQuestion(@ModelAttribute Question question, final ModelMap model, Principal principal) {
        question = questionService.askQuestion(question, principal.getName());
        model.addAttribute("question", question);
        return "redirect:/questions/" + question.getId();
    }

    @PostMapping(value = "/questions/{id}/answer")
    public String answerPost(@RequestParam String newAnswer, @PathVariable Integer id, Principal principal) {
        questionService.answerQuestion(id, newAnswer, principal.getName());
        return "redirect:/questions/" + id;
    }

    @GetMapping(value = "/questions/{id}")
    public String question(final ModelMap model, @PathVariable int id) {
        var question = questionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("question", question);
        model.addAttribute("answers", questionService.getAllAnswersByQuestionId(id));
        return "display-question";
    }

    @GetMapping(value = "/questions")
    public String questionAll(final ModelMap model, Principal principal) {
        var questions = questionService.getAllQuestionsByUser(principal.getName());
        model.addAttribute("questions", questions);
        model.addAttribute("author", principal.getName());
        return "listing-questions";
    }

    @GetMapping(value = "/questions/category/{category}")
    public String questionByCategory(final ModelMap model, @PathVariable String category) {
        var questions = questionService.getAllQuestionsByCategory(category);
        model.addAttribute("questions", questions);
        model.addAttribute("category", category);
        return "listing-questions";
    }

    @PostMapping("/questions/{questionId}/answers/{answerId}/like")
    public String likeAnswer(@PathVariable Integer questionId, @PathVariable Integer answerId) {
        questionService.increaseLikesForAnswer(answerId);
        return "redirect:/questions/" + questionId;
    }

    @PostMapping("/questions/{questionId}/answers/{answerId}/pin")
    public String togglePinStatus(@PathVariable Integer questionId, @PathVariable Integer answerId) {
        questionService.togglePinForAnswer(answerId);
        return "redirect:/questions/" + questionId;
    }

    @PostMapping("/questions/{questionId}/answers/{answerId}/comments")
    public String addComment(@PathVariable Long questionId,
                             @PathVariable Integer answerId,
                             @RequestParam String commentContent,
                             Principal principal) {
        String username = principal.getName();
        questionService.addComment(answerId, username, commentContent);
        return "redirect:/questions/" + questionId;
    }

}


