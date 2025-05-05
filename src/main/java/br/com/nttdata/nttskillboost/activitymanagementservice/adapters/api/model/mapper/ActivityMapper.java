package br.com.nttdata.nttskillboost.activitymanagementservice.adapters.api.model.mapper;

import br.com.nttdata.nttskillboost.activitymanagementservice.adapters.api.model.dto.ActivityRequest;
import br.com.nttdata.nttskillboost.activitymanagementservice.adapters.api.model.dto.ActivityResponse;
import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Activity;
import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.ActivityType;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper {

    public Activity toDomain(ActivityRequest dto) {
        return Activity.builder()
                .courseId(dto.getCourseId())
                .description(dto.getDescription())
                .point(dto.getPoint())
                .executeDate(dto.getExecuteDate())
                .availabilityDate(dto.getAvailabilityDate())
                .activityType(ActivityType.getActivityType(dto.getActivityType()))
                .activityPath(dto.getActivityPath())
                .build();
    }

    public ActivityResponse toResponse(Activity activity) {
        return ActivityResponse.builder()
                .id(activity.getId())
                .courseId(activity.getCourseId())
                .description(activity.getDescription())
                .point(activity.getPoint())
                .executeDate(activity.getExecuteDate())
                .availabilityDate(activity.getAvailabilityDate())
                .activityType(activity.getActivityType().name())
                .activityPath(activity.getActivityPath())
                .build();
    }
}