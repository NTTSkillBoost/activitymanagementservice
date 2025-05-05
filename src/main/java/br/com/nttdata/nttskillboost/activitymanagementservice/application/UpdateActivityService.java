package br.com.nttdata.nttskillboost.activitymanagementservice.application;

import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Activity;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.in.UpdateActivityUseCase;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.out.ActivityRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateActivityService implements UpdateActivityUseCase {

    private final ActivityRepositoryPort courseRepositoryPort;

    @Override
    public Activity update(UUID id, Activity activity) {
        Activity byId = courseRepositoryPort.findById(id);
        if (byId == null) {
            return null;
        }

        Activity activityUpdate = new Activity();
        activityUpdate.setId(byId.getId());
        activityUpdate.setCourseId(activity.getCourseId());
        activityUpdate.setDescription(activity.getDescription());
        activityUpdate.setPoint(activity.getPoint());
        activityUpdate.setExecuteDate(activity.getExecuteDate());
        activityUpdate.setAvailabilityDate(activity.getAvailabilityDate());
        activityUpdate.setActivityType(activity.getActivityType());
        activityUpdate.setActivityPath(activity.getActivityPath());
        activityUpdate.setStatus(activity.getStatus());

        return courseRepositoryPort.save(activityUpdate);
    }
}
