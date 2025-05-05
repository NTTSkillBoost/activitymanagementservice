package br.com.nttdata.nttskillboost.activitymanagementservice.application;

import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Activity;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.in.GetActivityUseCase;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.out.ActivityRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetActivityService implements GetActivityUseCase {

    private final ActivityRepositoryPort activityRepositoryPort;

    @Override
    public Activity findById(UUID id) {
        return activityRepositoryPort.findById(id);
    }

    @Override
    public List<Activity> findAll() {
        return activityRepositoryPort.findAll();
    }
}
