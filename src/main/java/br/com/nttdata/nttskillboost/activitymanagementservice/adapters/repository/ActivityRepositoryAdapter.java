package br.com.nttdata.nttskillboost.activitymanagementservice.adapters.repository;

import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Activity;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.out.ActivityRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ActivityRepositoryAdapter implements ActivityRepositoryPort {

    private final ActivityJpaRepository activityRepository;

    @Override
    public Activity save(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public Activity findById(UUID id) {
        Optional<Activity> byId = activityRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        } else {
            throw new EntityNotFoundException("Funcionário com ID " + id + " não encontrado");
        }
    }

    @Override
    public List<Activity> findAll() {
        return activityRepository.findAll();
    }

    @Override
    public Activity findByCourseId(UUID courseId) {
        return activityRepository.findByCourseId(courseId);
    }
}
