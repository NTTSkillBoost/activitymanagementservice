package br.com.nttdata.nttskillboost.activitymanagementservice.adapters.repository;

import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.ActivityEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ActivityEmployeeJpaRepository extends JpaRepository<ActivityEmployee, UUID> {
    ActivityEmployee findByEmployeeId(UUID employeeId);
}
