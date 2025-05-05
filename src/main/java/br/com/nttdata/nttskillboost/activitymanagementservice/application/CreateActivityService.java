package br.com.nttdata.nttskillboost.activitymanagementservice.application;

import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Activity;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.in.CreateActivityUseCase;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.out.ActivityRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateActivityService implements CreateActivityUseCase {

    private final ActivityRepositoryPort activityRepositoryPort;

    @Override
    public Activity create(Activity activity) {
        return activityRepositoryPort.save(activity);
    }
}
