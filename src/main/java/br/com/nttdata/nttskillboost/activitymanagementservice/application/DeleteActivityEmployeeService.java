package br.com.nttdata.nttskillboost.activitymanagementservice.application;

import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.ActivityEmployee;
import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Status;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.in.DeleteActivityEmployeeUseCase;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.out.ActivityEmployeeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteActivityEmployeeService implements DeleteActivityEmployeeUseCase {

    private final ActivityEmployeeRepositoryPort activityEmployeeRepositoryPort;

    @Override
    public boolean delete(UUID id) {
        ActivityEmployee byId = activityEmployeeRepositoryPort.findById(id);
        if (byId != null) {
            byId.setStatus(Status.DELETED);
            activityEmployeeRepositoryPort
                    .save(byId);
            return true;
        } else {
            return false;
        }
    }
}
