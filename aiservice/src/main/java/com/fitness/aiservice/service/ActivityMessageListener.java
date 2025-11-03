package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener
{

    private final ActivityAIService activityAIService;
    private final RecommendationRepository  recommendationRepository;

    // By the help of kafka listener, in processActivity() method what ever data comes, it will map to Activity object
    // and the userId will print in the form of log
    // so this an asynchronous communication
    @KafkaListener(topics = "${kafka.topic.name}", groupId = "activity-processor-group")
    public void processActivity(Activity activity)
    {
        log.info("Received activity for processing: {}", activity.getUserId());
        Recommendation recommendation = activityAIService.generateRecommendation(activity);
        recommendationRepository.save(recommendation);
    }
}