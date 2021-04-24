package ru.project.quiz.service.quiz.Impl;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import ru.project.quiz.domain.entity.quiz.QuizSample;
import ru.project.quiz.handler.exception.QuizAPPException;
import ru.project.quiz.repository.quiz.QuizSampleRepository;
import ru.project.quiz.service.quiz.QuizSampleService;

@Service
public class QuizSampleServiceImpl implements QuizSampleService {

    public final QuizSampleRepository quizSampleRepository;

    public QuizSampleServiceImpl(QuizSampleRepository quizSampleRepository) {
        this.quizSampleRepository = quizSampleRepository;
    }

    @Override
    public QuizSample getSample(long id) {
        if (quizSampleRepository.findById(id).isEmpty()) {
            throw new QuizAPPException("Sample not found with id: " + id);
        } else {
            return quizSampleRepository.findById(id).get();
        }
    }

    @Override
    public QuizSample saveSample(QuizSample quizSample) {
        if (isExistSample(quizSample)) {
            throw new QuizAPPException("Сэмпл Существует");
        }
        quizSampleRepository.save(quizSample);
        return quizSample;
    }

    @Override
    public QuizSample editSample(QuizSample quizSample) {
        quizSampleRepository.findById(quizSample.getId())
                .map(quizSampleRepository::save)
                .orElseThrow(() -> new QuizAPPException("Sample not found"));
        return quizSample;
    }

    @Override
    public void deleteSample(long id) {
        if (quizSampleRepository.findById(id).isEmpty()) {
            throw new QuizAPPException("Sample not found with id: " + id);
        } else {
            quizSampleRepository.deleteById(id);
        }
    }

    private boolean isExistSample(ru.project.quiz.domain.entity.quiz.QuizSample quizSample) {
        Example<ru.project.quiz.domain.entity.quiz.QuizSample> example = Example.of(quizSample, modelToCheckExistQuestion());
        return quizSampleRepository.exists(example);
    }

    private ExampleMatcher modelToCheckExistQuestion() {
        return ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withIgnoreCase("name");
    }

}
