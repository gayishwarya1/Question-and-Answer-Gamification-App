package fr.utcapitole.demo.api;

import fr.utcapitole.demo.entities.Question;
import fr.utcapitole.demo.repositories.QuestionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/question")
public class QuestionController {
    private final QuestionRepository repository;

    public QuestionController(QuestionRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Question get(@PathVariable int id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/", produces = "application/json")
    public void clear() {
        repository.deleteAll();
    }

}
