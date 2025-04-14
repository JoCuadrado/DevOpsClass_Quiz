package edu.esiea.back.service;

import edu.esiea.back.model.*;
import edu.esiea.back.repository.*;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class QuizService {
    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public Optional<Quiz> getQuiz(Long id) {
        return quizRepository.findById(id);
    }

    public int submitAnswers(Long quizId, Map<Long, Long> userAnswers) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        if (quizOptional.isEmpty()) return 0;

        Quiz quiz = quizOptional.get();
        int score = 0;

        for (Question question : quiz.getQuestions()) {
            Long selectedAnswerId = userAnswers.get(question.getId());
            if (selectedAnswerId == null) continue;

            for (Answer answer : question.getAnswers()) {
                if (answer.getId().equals(selectedAnswerId) && answer.isCorrect()) {
                    score++;
                    break;
                }
            }
        }
        return score;
    }
}