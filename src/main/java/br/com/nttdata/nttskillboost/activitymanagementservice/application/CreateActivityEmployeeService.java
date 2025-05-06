package br.com.nttdata.nttskillboost.activitymanagementservice.application;

import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.ActivityEmployee;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.in.CreateActivityEmployeeUseCase;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.out.ActivityEmployeeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateActivityEmployeeService implements CreateActivityEmployeeUseCase {

    private final ActivityEmployeeRepositoryPort activityEmployeeRepositoryPort;

    @Override
    public ActivityEmployee create(ActivityEmployee activityEmployee) {
        return activityEmployeeRepositoryPort.save(activityEmployee);
    }
}
