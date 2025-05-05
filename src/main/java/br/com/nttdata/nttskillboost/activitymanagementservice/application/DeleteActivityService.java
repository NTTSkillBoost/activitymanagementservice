package br.com.nttdata.nttskillboost.activitymanagementservice.application;

import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Activity;
import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Status;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.in.DeleteActivityUseCase;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.out.ActivityRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteActivityService implements DeleteActivityUseCase {

    private final ActivityRepositoryPort activityRepositoryPort;

    @Override
    public boolean delete(UUID id) {
        Activity byId = activityRepositoryPort.findById(id);
        if (byId != null) {
            byId.setStatus(Status.DELETED);
            activityRepositoryPort.save(byId);
            return true;
        } else {
            return false;
        }
    }
}
