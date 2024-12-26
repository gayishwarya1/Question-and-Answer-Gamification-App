package fr.utcapitole.demo.gui;

import fr.utcapitole.demo.dto.UserDto;
import fr.utcapitole.demo.entities.User;
import fr.utcapitole.demo.service.UserProfileService;
import fr.utcapitole.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private UserProfileService userProfileService ;

    public String getUser(User user) {
        return user.getUsername() ;
    }

    @GetMapping("/{username}")
    public String userprofile(@PathVariable String username, Model model) {
        Optional<UserDto> userOptional = userService.getUserByUsername(username);
        if (userOptional.isPresent()) {
            var categories = userProfileService.getCategories();
            var unansweredQuestions = userProfileService.getUnansweredQuestions();

            model.addAttribute("user", userOptional.get());
            model.addAttribute("categories", categories);
            model.addAttribute("unansweredQuestions", unansweredQuestions);

            return "user-profile";
        } else {
            return "userNotFound";
        }
    } }
