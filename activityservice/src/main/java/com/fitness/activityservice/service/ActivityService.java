package com.fitness.activityservice.service;

import com.fitness.activityservice.converter.ActivityConverter;
import com.fitness.activityservice.dto.request.ActivityRequest;
import com.fitness.activityservice.dto.response.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;
    private final KafkaTemplate<String, Activity> kafkaTemplate; // we send data by this

    @Value("${kafka.topic.name}")
    private String topicName;

    public ActivityResponse trackActivity(ActivityRequest request) {

        Boolean isValidUser = userValidationService.validateUser(request.getUserId());

        if(!isValidUser) {
            throw new RuntimeException("Invalid User: " + request.getUserId());
        }

        Activity activity = ActivityConverter.activityRequestToActivity(request);
        Activity savedActivity = activityRepository.save(activity);

        // we are sending data to kafka
        try
        {
            kafkaTemplate.send(topicName, savedActivity.getUserId(), savedActivity);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return ActivityConverter.activityToActivityResponse(savedActivity);
    }

    public List<ActivityResponse> getUserActivities(String userId) {
        List<Activity> activityList = activityRepository.findByUserId((userId));
        return activityList.stream()
                .map(ActivityConverter::activityToActivityResponse) // method reference in stream
                .collect(Collectors.toList());
    }
}
