package br.com.nttdata.nttskillboost.activitymanagementservice.ports.in;

import java.util.UUID;

public interface DeleteActivityUseCase {
    boolean delete(UUID id);
}
