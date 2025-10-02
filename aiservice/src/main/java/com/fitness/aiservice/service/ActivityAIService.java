package com.fitness.aiservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
// this service will process the response of AI
public class ActivityAIService {

    private final GeminiService geminiService;

    public Recommendation generateRecommendation(Activity activity)
    {
        String prompt = createPromptForActivity(activity);
        String aiResponse = geminiService.getRecommendations(prompt);
        log.info("RESPONSE FROM AI: {}", aiResponse);
        return processAiResponse(activity, aiResponse);
    }

    private Recommendation processAiResponse(Activity activity, String aiResponse)
    {
        try
        {
            // it is comming from JackSon, By the help of this we can work with JSON in java
            ObjectMapper objectMapper = new ObjectMapper();

            // here i am converting aiResponse to JSON node object i.e JSON tree so that we can navigate in JSON
            JsonNode rootNode = objectMapper.readTree(aiResponse);

            // navigate means we can move from key -> another key -> value in JSON (nested structure)
            JsonNode textNode = rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .get("parts")
                    .get(0)
                    .path("text");

            // asText() return the given information as a string and removes all the special characters like json\n, \n
            // means i am cleaning the data
            String jsonContent = textNode.asText()
                    .replaceAll("```json\\n", "")
                    .replaceAll("\\n", "")
                    .trim();

            // log.info("RESPONSE FROM CLEANED AI: {}", jsonContent);

            // after extract the data, it will store in the database
            // we are extracting analysis items into Human Readable format not the JSON format
            JsonNode analysisJson = objectMapper.readTree(jsonContent);
            JsonNode analysisNode = analysisJson.path("analysis");

            StringBuilder fullAnalysis = new StringBuilder();

            //                                                    Json keyname   Our keyname
            addAnalysisSection(fullAnalysis, analysisNode, "overall", "Overall: ");
            addAnalysisSection(fullAnalysis, analysisNode, "pace", "Pace: ");
            addAnalysisSection(fullAnalysis, analysisNode, "heartRate", "Heart Rate: ");
            addAnalysisSection(fullAnalysis, analysisNode, "caloriesBurned", "Calories: ");

            List<String> improvements = extractImprovements(analysisJson.path("improvements"));
            List<String> suggestions = extractSuggestions(analysisJson.path("suggestions"));
            List<String> safety = extractSafetyGuidelines(analysisJson.path("saftey"));

            return Recommendation.builder()
                    .activityId(activity.getId())
                    .userId(activity.getUserId())
                    .type(activity.getType().toString())
                    .recommendation(fullAnalysis.toString().trim())
                    .improvements(improvements)
                    .suggestions(suggestions)
                    .safety(safety)
                    .createdAt(LocalDateTime.now())
                    .build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return createDefaultRecommendation(activity);
        }
    }

    private Recommendation createDefaultRecommendation(Activity activity)
    {
        return Recommendation.builder()
                .activityId(activity.getId())
                .userId(activity.getUserId())
                .type(activity.getType().toString())
                .recommendation("Unable to generate detailed analysis")
                .improvements(Collections.singletonList("Continue with your current routine"))
                .suggestions(Collections.singletonList("Consider consulting a fitness consultant"))
                .safety(Arrays.asList(
                        "Always warm up before exercise",
                        "Stay hyderated",
                        "Listen to your body"
                ))
                .createdAt(LocalDateTime.now())
                .build();
    }

    private List<String> extractSafetyGuidelines(JsonNode safteyNode)
    {
        List<String> safety = new ArrayList<>();
        if(safteyNode.isArray())
        {
            safteyNode.forEach(item -> safety.add(item.asText()));
        }
        return safety.isEmpty() ?
                Collections.singletonList("Follow general safety guidelines") :
                safety;
    }

    private List<String> extractSuggestions(JsonNode suggestionsNode)
    {
        List<String> suggestions = new ArrayList<>();
        if(suggestionsNode.isArray())
        {
            suggestionsNode.forEach(suggestion -> {
                String area = suggestion.path("workout").asText();
                String detail = suggestion.path("description").asText();

                suggestions.add(String.format("%s: %s",area, detail));
            });
        }
        return suggestions.isEmpty() ?
                Collections.singletonList("No specific suggestions provided") :
                suggestions;
    }

    private List<String> extractImprovements(JsonNode improvementsNode)
    {
        List<String> improvements = new ArrayList<>();
        if(improvementsNode.isArray())
        {
            improvementsNode.forEach(improvement -> {
                String area = improvement.path("area").asText();
                String detail = improvement.path("recommendation").asText();

                improvements.add(String.format("%s: %s",area, detail));
            });
        }
        return improvements.isEmpty() ?
                Collections.singletonList("No specific improvements provided") :
                improvements;
    }

    //    "overall": "This was a solid moderate-duration    - AI model(JSON) format
    //    Overall: This was a solid moderate-duration       - my format that will be shown to user
    //    so i am transforming the format so that it will Human readable form
    private void addAnalysisSection(StringBuilder fullAnalysis, JsonNode analysisNode, String key, String prefix)
    {
        if(!analysisNode.path(key).isMissingNode())
        {
            fullAnalysis.append(prefix)
                    .append(analysisNode.path(key).asText())
                    .append("\n\n");
        }
    }

    private String createPromptForActivity(Activity activity)
    {
        return String.format("""
                Analyze this fitness activity and provide detailed recommendations in the following EXACT JSON format:
                {
                    "analysis": {
                        "overall": "Overall analysis here",
                        "pace": "Pace analysis here",
                        "heartRate": "Heart rate analysis here",
                        "caloriesBurned": "Calories analysis here",
                    },
                    "improvements": [
                        {
                            "area": "Area name",
                            "recommendation": ""Detailed recommendation"
                        }
                    ],
                    "suggestions": [
                        {
                            "workout": "Workout name",
                            "description": "Detailed workout description"
                        }
                    ],
                    "safety": [
                        "Safety point 1",
                        "Safety point 2"
                    ]
                }
                
                Analyze this activity:
                Activity Type: %s
                Duration: %d minutes
                Calories Burned: %d
                Additional Metrics: %s
                
                Provide detailed analysis focusing on performance, improvents, next workout suggestions, and safety guidelines.
                Ensure the response follows the EXACT JSON format shown above.
                """,
                        activity.getType(),
                        activity.getDuration(),
                        activity.getCaloriesBurned(),
                        activity.getAdditionalMetrics()
                );
    }
}
