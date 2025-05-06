package br.com.nttdata.nttskillboost.activitymanagementservice.ports.in;

import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.ActivityEmployee;

public interface CreateActivityEmployeeUseCase {
    ActivityEmployee create(ActivityEmployee activityEmployee);
}
