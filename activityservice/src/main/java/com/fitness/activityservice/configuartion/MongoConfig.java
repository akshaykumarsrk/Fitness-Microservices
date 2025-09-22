package com.fitness.activityservice.configuartion;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing  // it enable mongo annotations
public class MongoConfig {
}
