package br.com.nttdata.nttskillboost.activitymanagementservice.application;

import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Activity;
import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.ActivityEmployee;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.in.GetActivityEmployeeUseCase;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.in.GetActivityUseCase;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.out.ActivityEmployeeRepositoryPort;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.out.ActivityRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetActivityEmployeeService implements GetActivityEmployeeUseCase {

    private final ActivityEmployeeRepositoryPort activityEmployeeRepositoryPort;

    @Override
    public ActivityEmployee findById(UUID id) {
        return activityEmployeeRepositoryPort.findById(id);
    }

    @Override
    public List<ActivityEmployee> findAll() {
        return activityEmployeeRepositoryPort.findAll();
    }
}
