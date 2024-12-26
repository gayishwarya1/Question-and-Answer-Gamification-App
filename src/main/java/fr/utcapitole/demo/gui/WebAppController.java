package fr.utcapitole.demo.gui;

import fr.utcapitole.demo.service.HomeService;
import fr.utcapitole.demo.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import fr.utcapitole.demo.service.LeaderBoardService;

@Controller
public class WebAppController {
    @Autowired
    private HomeService homeService;



    @Autowired
    private LeaderBoardService leaderBoardService;

    @GetMapping(value = "/")
    public String home(final ModelMap model) {
        var categories = homeService.getCategories();
        model.addAttribute("categories", categories);
        var unansweredQuestions = homeService.getUnansweredQuestions();
        model.addAttribute("unansweredQuestions", unansweredQuestions);
        return "home";
    }



    @GetMapping(value = "/leaderboard")
    public String getLeaderBoard(final ModelMap model) {
        model.addAttribute("leaderboard", leaderBoardService.getLeaderBoard());
        return "leaderboard"; // Return the name of the Thymeleaf template
    }
}
