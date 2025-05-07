package br.com.nttdata.nttskillboost.activitymanagementservice.application;

import br.com.nttdata.nttskillboost.activitymanagementservice.adapters.gateway.CourseClient;
import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Activity;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.in.CreateActivityUseCase;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.out.ActivityRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateActivityService implements CreateActivityUseCase {

    private final ActivityRepositoryPort activityRepositoryPort;
    private final CourseClient courseClient;
    @Override
    public Activity create(Activity activity) {

        boolean courseExists = courseClient.existsById(activity.getCourseId());
        if (!courseExists) {
            throw new IllegalArgumentException("Curso não existe.");
        }

        return activityRepositoryPort.save(activity);
    }
}
