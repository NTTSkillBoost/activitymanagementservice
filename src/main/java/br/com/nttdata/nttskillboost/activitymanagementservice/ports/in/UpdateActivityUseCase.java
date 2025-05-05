package br.com.nttdata.nttskillboost.activitymanagementservice.ports.in;

import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Activity;

import java.util.UUID;

public interface UpdateActivityUseCase {
    Activity update(UUID id, Activity employeeRole);
}
