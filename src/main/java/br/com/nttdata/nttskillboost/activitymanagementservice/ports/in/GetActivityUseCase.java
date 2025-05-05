package br.com.nttdata.nttskillboost.activitymanagementservice.ports.in;

import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Activity;

import java.util.List;
import java.util.UUID;

public interface GetActivityUseCase {
    Activity findById(UUID id);
    List<Activity> findAll();
}
