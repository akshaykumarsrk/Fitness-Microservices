package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Activity;
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

    // By the help of kafka listener, in processActivity() method what ever data comes, it will map to Activity object
    // and the userId will print in the form of log
    // so this an asynchronous communication
    @KafkaListener(topics = "${kafka.topic.name}", groupId = "activity-processor-group")
    public void processActivity(Activity activity)
    {
        log.info("Received activity for processing: {}", activity.getUserId());
        activityAIService.generateRecommendation(activity);
    }
}
