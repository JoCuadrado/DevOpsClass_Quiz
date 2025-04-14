package edu.esiea.back.service;

import edu.esiea.back.model.*;
import edu.esiea.back.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class QuizServiceTest {

	@Mock
	private QuizRepository quizRepository;

	@Mock
	private QuestionRepository questionRepository;

	@Mock
	private AnswerRepository answerRepository;

	@InjectMocks
	private QuizService quizService;

	private Quiz quiz;
	private Question question;
	private Answer answerCorrect;
	private Answer answerIncorrect;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		// Création des données simulées avec IDs
		answerCorrect = new Answer("Réponse correcte", true, null);
		answerCorrect.setId(101L);

		answerIncorrect = new Answer("Réponse incorrecte", false, null);
		answerIncorrect.setId(102L);

		List<Answer> answers = new ArrayList<>();
		answers.add(answerCorrect);
		answers.add(answerIncorrect);

		question = new Question("Quel est le capital de la France ?", null, answers);
		question.setId(201L);

		List<Question> questions = new ArrayList<>();
		questions.add(question);

		quiz = new Quiz("Géographie", questions);
		quiz.setId(301L);

		// Lier les objets correctement
		answerCorrect.setQuestion(question);
		answerIncorrect.setQuestion(question);
		question.setQuiz(quiz);
	}

	@Test
	void testSubmitAnswers() {
		Map<Long, Long> userAnswers = new HashMap<>();
		userAnswers.put(question.getId(), answerCorrect.getId());

		when(quizRepository.findById(anyLong())).thenReturn(Optional.of(quiz));

		int score = quizService.submitAnswers(1L, userAnswers);

		assertEquals(1, score, "Le score doit être égal à 1 car la réponse est correcte");
		verify(quizRepository, times(1)).findById(1L);
	}

	@Test
	void testSubmitAnswersWithIncorrectAnswer() {
		Map<Long, Long> userAnswers = new HashMap<>();
		userAnswers.put(question.getId(), answerIncorrect.getId());

		when(quizRepository.findById(anyLong())).thenReturn(Optional.of(quiz));

		int score = quizService.submitAnswers(1L, userAnswers);

		assertEquals(0, score, "Le score doit être égal à 0 car la réponse est incorrecte");
		verify(quizRepository, times(1)).findById(1L);
	}

	@Test
	void testSubmitAnswersWithNoCorrectAnswer() {
		Map<Long, Long> userAnswers = new HashMap<>();

		when(quizRepository.findById(anyLong())).thenReturn(Optional.of(quiz));

		int score = quizService.submitAnswers(1L, userAnswers);

		assertEquals(0, score, "Le score doit être égal à 0 car il n'y a pas de réponse donnée");
	}

	@Test
	void testSubmitAnswersWithInvalidQuizId() {
		Map<Long, Long> userAnswers = new HashMap<>();

		when(quizRepository.findById(anyLong())).thenReturn(Optional.empty());

		int score = quizService.submitAnswers(999L, userAnswers);

		assertEquals(0, score, "Le score doit être égal à 0 car le quiz est introuvable");
	}

}
