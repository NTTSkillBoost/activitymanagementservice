package br.com.nttdata.nttskillboost.activitymanagementservice.application;

import br.com.nttdata.nttskillboost.activitymanagementservice.adapters.gateway.EnrollmentClient;
import br.com.nttdata.nttskillboost.activitymanagementservice.adapters.gateway.ProgressEventPublisher;
import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Activity;
import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.ActivityEmployee;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.in.CreateActivityEmployeeUseCase;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.out.ActivityEmployeeRepositoryPort;
import br.com.nttdata.nttskillboost.activitymanagementservice.ports.out.ActivityRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateActivityEmployeeService implements CreateActivityEmployeeUseCase {

    private final ActivityEmployeeRepositoryPort activityEmployeeRepositoryPort;
    private final ProgressEventPublisher progressEventPublisher;
    private final ActivityRepositoryPort activityRepositoryPort;
    private final EnrollmentClient enrollmentClient;

    @Override
    public ActivityEmployee create(ActivityEmployee activityEmployee) throws Exception {
        Activity byId = activityRepositoryPort.findById(activityEmployee.getActivityId());
        if (byId == null) {
            return null;
        }

        UUID courseId = byId.getCourseId();
        UUID employeeId = activityEmployee.getEmployeeId(); //Empregado elegivel como aluno

        boolean isEnrolled = enrollmentClient.isStudentEnrolled(employeeId, courseId);

        if (!isEnrolled) {
            throw new Exception("Funcionário não está inscrito no curso da atividade.");
        }

        ActivityEmployee save = activityEmployeeRepositoryPort.save(activityEmployee);
        progressEventPublisher.sendProgressEvent(employeeId, courseId, "COMPLETED");
        return save;
    }
}