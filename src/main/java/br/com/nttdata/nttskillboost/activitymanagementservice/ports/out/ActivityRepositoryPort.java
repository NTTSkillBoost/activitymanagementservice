package br.com.nttdata.nttskillboost.activitymanagementservice.ports.out;

import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Activity;

import java.util.List;
import java.util.UUID;

public interface ActivityRepositoryPort {
    Activity save(Activity activity);
    Activity findById(UUID id);
    List<Activity> findAll();
    Activity findByCourseId(UUID courseId);
}
