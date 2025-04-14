package edu.esiea.back.controller;

import edu.esiea.back.model.*;
import edu.esiea.back.service.QuizService;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/create")
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        return quizService.createQuiz(quiz);
    }

    @GetMapping("/{id}")
    public Optional<Quiz> getQuiz(@PathVariable Long id) {
        return quizService.getQuiz(id);
    }

    @PostMapping("/submit/{id}")
    public Map<String, Integer> submitQuiz(@PathVariable Long id, @RequestBody Map<Long, Long> userAnswers) {
        int score = quizService.submitAnswers(id, userAnswers);
        return Map.of("score", score);
    }
}