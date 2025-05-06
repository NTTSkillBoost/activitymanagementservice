package br.com.nttdata.nttskillboost.activitymanagementservice.ports.in;

import java.util.UUID;

public interface DeleteActivityEmployeeUseCase {
    boolean delete(UUID id);
}
