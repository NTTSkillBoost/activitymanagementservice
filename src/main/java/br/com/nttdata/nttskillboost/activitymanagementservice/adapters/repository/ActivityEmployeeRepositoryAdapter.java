package br.com.nttdata.nttskillboost.activitymanagementservice.adapters.repository;

import br.com.nttdata.nttskillboost.activitymanagementservice.adapters.gateway.EmployeeClient;
import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.ActivityEmployee;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.out.ActivityEmployeeRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ActivityEmployeeRepositoryAdapter implements ActivityEmployeeRepositoryPort {

    private final ActivityEmployeeJpaRepository activityRepository;
    private final EmployeeClient employeeClient;

    @Override
    public ActivityEmployee save(ActivityEmployee activityEmployee) {
        boolean employeeStudentExists = employeeClient.existsById(activityEmployee.getEmployeeId());
        if (!employeeStudentExists) {
            throw new EntityNotFoundException("Funcionário com ID " + activityEmployee.getEmployeeId() + " não encontrado");
        }

        return activityRepository.save(activityEmployee);
    }

    @Override
    public ActivityEmployee findById(UUID id) {
        Optional<ActivityEmployee> byId = activityRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        } else {
            throw new EntityNotFoundException("Funcionário com ID " + id + " não encontrado");
        }
    }

    @Override
    public List<ActivityEmployee> findAll() {
        return activityRepository.findAll();
    }

    @Override
    public ActivityEmployee findByEmployeeId(UUID courseId) {
        return activityRepository.findByEmployeeId(courseId);
    }
}
