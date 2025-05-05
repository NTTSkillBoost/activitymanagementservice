package br.com.nttdata.nttskillboost.activitymanagementservice.adapters.repository;

import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ActivityJpaRepository extends JpaRepository<Activity, UUID> {
    Activity findByName(String name);
}
