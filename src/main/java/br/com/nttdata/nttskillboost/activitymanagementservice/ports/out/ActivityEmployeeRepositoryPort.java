package br.com.nttdata.nttskillboost.activitymanagementservice.ports.out;

import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.ActivityEmployee;

import java.util.List;
import java.util.UUID;

public interface ActivityEmployeeRepositoryPort {
    ActivityEmployee save(ActivityEmployee activityEmployee);
    ActivityEmployee findById(UUID id);
    List<ActivityEmployee> findAll();
    ActivityEmployee findByEmployeeId(UUID courseId);
}
