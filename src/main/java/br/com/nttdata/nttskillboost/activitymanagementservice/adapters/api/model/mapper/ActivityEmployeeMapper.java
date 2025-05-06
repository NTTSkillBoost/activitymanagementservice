package br.com.nttdata.nttskillboost.activitymanagementservice.adapters.api.model.mapper;

import br.com.nttdata.nttskillboost.activitymanagementservice.adapters.api.model.dto.ActivityEmployeeRequest;
import br.com.nttdata.nttskillboost.activitymanagementservice.adapters.api.model.dto.ActivityEmployeeResponse;
import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.ActivityEmployee;
import org.springframework.stereotype.Component;

@Component
public class ActivityEmployeeMapper {

    public ActivityEmployee toDomain(ActivityEmployeeRequest dto) {
        return ActivityEmployee.builder()
                .activityId(dto.getActivityId())
                .employeeId(dto.getEmployeeId())
                .description(dto.getDescription())
                .percentageConcluded(dto.getPercentageConcluded())
                .concludedDate(dto.getConcludedDate())
                .build();
    }

    public ActivityEmployeeResponse toResponse(ActivityEmployee activityEmployee) {
        return ActivityEmployeeResponse.builder()
                .id(activityEmployee.getId())
                .activityId(activityEmployee.getActivityId())
                .employeeId(activityEmployee.getEmployeeId())
                .description(activityEmployee.getDescription())
                .percentageConcluded(activityEmployee.getPercentageConcluded())
                .concludedDate(activityEmployee.getConcludedDate())
                .build();
    }
}