package br.com.nttdata.nttskillboost.activitymanagementservice.adapters.api.controller;

import br.com.nttdata.nttskillboost.activitymanagementservice.adapters.api.model.dto.ActivityEmployeeRequest;
import br.com.nttdata.nttskillboost.activitymanagementservice.adapters.api.model.dto.ActivityEmployeeResponse;
import br.com.nttdata.nttskillboost.activitymanagementservice.adapters.api.model.mapper.ActivityEmployeeMapper;
import br.com.nttdata.nttskillboost.activitymanagementservice.application.CreateActivityEmployeeService;
import br.com.nttdata.nttskillboost.activitymanagementservice.application.DeleteActivityEmployeeService;
import br.com.nttdata.nttskillboost.activitymanagementservice.application.GetActivityEmployeeService;
import br.com.nttdata.nttskillboost.activitymanagementservice.application.UpdateActivityEmployeeService;
import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.ActivityEmployee;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1/activity-employees")
@RequiredArgsConstructor
public class ActivityEmployeeController {

    private final CreateActivityEmployeeService createActivityEmployeeService;
    private final GetActivityEmployeeService getActivityEmployeeService;
    private final UpdateActivityEmployeeService updateActivityEmployeeService;
    private final DeleteActivityEmployeeService deleteActivityEmployeeService;
    private final ActivityEmployeeMapper activityEmployeeMapper;

    @Retry(name = "activityEmployeeService", fallbackMethod = "fallbackCreate")
    @CircuitBreaker(name = "activityEmployeeService", fallbackMethod = "fallbackCreate")
    @Bulkhead(name = "activityEmployeeService")
    @PostMapping
    public ResponseEntity<ActivityEmployeeResponse> create(@Valid @RequestBody ActivityEmployeeRequest dto) {
        ActivityEmployee activityEmployee = activityEmployeeMapper.toDomain(dto);
        ActivityEmployee created = createActivityEmployeeService.create(activityEmployee);
        if (created == null) {
            return ResponseEntity.badRequest().build();
        }

        URI location = URI.create(String.format("/v1/activity-employees/%s", created.getId()));
        ActivityEmployeeResponse response = activityEmployeeMapper.toResponse(created);
        return ResponseEntity.created(location).body(response);
    }

    // 🔎 Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<ActivityEmployeeResponse> findById(@PathVariable UUID id) {
        ActivityEmployee activityEmployeeById = getActivityEmployeeService.findById(id);
        if (activityEmployeeById != null) {
            ActivityEmployeeResponse byId = activityEmployeeMapper.toResponse(activityEmployeeById);
            return ResponseEntity.ok(byId);
        }

        return ResponseEntity.notFound().build();
    }

    // 🔎 Listar todos
    @GetMapping
    public List<ActivityEmployeeResponse> listAll() {
        List<ActivityEmployee> cours = getActivityEmployeeService.findAll();
        return cours.stream()
                .map(activityEmployeeMapper::toResponse)
                .toList();
    }

    // 🔄 Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<ActivityEmployeeResponse> update(@PathVariable UUID id, @Valid @RequestBody ActivityEmployeeRequest dto) {
        ActivityEmployee activity = activityEmployeeMapper.toDomain(dto);
        ActivityEmployee update = updateActivityEmployeeService.update(id, activity);
        if (update != null) {
            ActivityEmployeeResponse response = activityEmployeeMapper.toResponse(update);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    // ❌ Deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (deleteActivityEmployeeService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<ActivityEmployeeResponse> fallbackCreate(ActivityEmployeeRequest dto, Throwable t) {
        ActivityEmployee fallback = new ActivityEmployee();
        fallback.setId(UUID.randomUUID());
        fallback.setDescription("Fallback description");
        fallback.setPercentageConcluded(dto.getPercentageConcluded());
        fallback.setEmployeeId(dto.getEmployeeId());
        fallback.setDescription(dto.getDescription());

        ActivityEmployeeResponse response = activityEmployeeMapper.toResponse(fallback);

        if (t instanceof BulkheadFullException || t instanceof CallNotPermittedException) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
