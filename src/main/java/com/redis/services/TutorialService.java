package com.redis.services;


import com.redis.entities.Tutorial;
import com.redis.repositories.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@EnableCaching
public class TutorialService {
    @Autowired
    private TutorialRepository tutorialRepository;

    private void doLongRunningTask() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Cacheable("tutorials")
    public List<Tutorial> findAll() {
        doLongRunningTask();

        return tutorialRepository.findAll();
    }

    @Cacheable("tutorials")
    public List<Tutorial> findByTitleContaining(String title) {
        doLongRunningTask();

        return tutorialRepository.findByTitleContaining(title);
    }

    @Cacheable("tutorial")
    public Optional<Tutorial> findById(long id) {
        doLongRunningTask();

        return tutorialRepository.findById(id);
    }

    public Tutorial save(Tutorial tutorial) {
        return tutorialRepository.save(tutorial);
    }

    @CacheEvict(value = "tutorial", key = "#tutorial.id")
    public Tutorial update(Tutorial tutorial) {
        return tutorialRepository.save(tutorial);
    }

    @CacheEvict(value = "tutorial", key = "#id")
    public void deleteById(long id) {
        tutorialRepository.deleteById(id);
    }

    @CacheEvict(value = { "tutorial", "tutorials", "published_tutorials" }, allEntries = true)
    public void deleteAll() {
        tutorialRepository.deleteAll();
    }

    @Cacheable("published_tutorials")
    public List<Tutorial> findByPublished(boolean isPublished) {
        doLongRunningTask();

        return tutorialRepository.findByPublished(isPublished);
    }


}
