package br.com.nttdata.nttskillboost.activitymanagementservice.ports.in;

import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.ActivityEmployee;

import java.util.List;
import java.util.UUID;

public interface GetActivityEmployeeUseCase {
    ActivityEmployee findById(UUID id);
    List<ActivityEmployee> findAll();
}
