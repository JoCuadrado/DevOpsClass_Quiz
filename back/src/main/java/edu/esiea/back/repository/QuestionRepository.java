package edu.esiea.back.repository;

import edu.esiea.back.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {}
