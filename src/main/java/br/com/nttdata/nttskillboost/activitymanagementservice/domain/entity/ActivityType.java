package br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity;

import lombok.Getter;

@Getter
public enum ActivityType {
    VIDEO("VIDEO"), // Work In Progress
    QUIZ("QUIZ"), // Completed
    TASK("TASK");   // Archived
    // Add more statuses as needed

    private final String type;

    private ActivityType(String type) {
        this.type = type;
    }

    public static ActivityType getActivityType(String type) {
        for (ActivityType activityType : ActivityType.values()) {
            if (activityType.name().equalsIgnoreCase(type)) {
                return activityType;
            }
        }
        throw new IllegalArgumentException("No constant with text " + type + " found");
    }
}
