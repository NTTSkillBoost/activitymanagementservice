package br.com.nttdata.nttskillboost.activitymanagementservice.ports.in;

import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.ActivityEmployee;

import java.util.UUID;

public interface UpdateActivityEmployeeUseCase {
    ActivityEmployee update(UUID id, ActivityEmployee activityEmployee);
}
