package br.com.nttdata.nttskillboost.activitymanagementservice.application;

import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Activity;
import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.ActivityEmployee;
import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.ActivityType;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.in.UpdateActivityEmployeeUseCase;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.in.UpdateActivityUseCase;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.out.ActivityEmployeeRepositoryPort;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.out.ActivityRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateActivityEmployeeService implements UpdateActivityEmployeeUseCase {

    private final ActivityEmployeeRepositoryPort courseRepositoryPort;

    @Override
    public ActivityEmployee update(UUID id, ActivityEmployee activityEmployee) {
        ActivityEmployee byId = courseRepositoryPort.findById(id);
        if (byId == null) {
            return null;
        }

        ActivityEmployee activityEmployeeUpdate = new ActivityEmployee();
        activityEmployeeUpdate.setId(byId.getId());
        activityEmployeeUpdate.setDescription(activityEmployee.getDescription());
        activityEmployeeUpdate.setPercentageConcluded(activityEmployee.getPercentageConcluded());
        activityEmployeeUpdate.setConcludedDate(activityEmployee.getConcludedDate());

        return courseRepositoryPort.save(activityEmployeeUpdate);
    }
}
