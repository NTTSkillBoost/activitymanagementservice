package br.com.nttdata.nttskillboost.activitymanagementservice.ports.in;

import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Activity;

public interface CreateActivityUseCase {
    Activity create(Activity activity);
}
