package br.com.nttdata.nttskillboost.activitymanagementservice.adapters.api.controller;

import br.com.nttdata.nttskillboost.activitymanagementservice.adapters.api.model.dto.ActivityRequest;
import br.com.nttdata.nttskillboost.activitymanagementservice.adapters.api.model.dto.ActivityResponse;
import br.com.nttdata.nttskillboost.activitymanagementservice.adapters.api.model.mapper.ActivityMapper;
import br.com.nttdata.nttskillboost.activitymanagementservice.application.CreateActivityService;
import br.com.nttdata.nttskillboost.activitymanagementservice.application.DeleteActivityService;
import br.com.nttdata.nttskillboost.activitymanagementservice.application.GetActivityService;
import br.com.nttdata.nttskillboost.activitymanagementservice.application.UpdateActivityService;
import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Activity;
import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.ActivityType;
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
@RequestMapping("/v1/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final CreateActivityService createActivityService;
    private final GetActivityService getActivityService;
    private final UpdateActivityService updateActivityService;
    private final DeleteActivityService deleteActivityService;
    private final ActivityMapper activityMapper;

    @Retry(name = "activityService", fallbackMethod = "fallbackCreate")
    @CircuitBreaker(name = "activityService", fallbackMethod = "fallbackCreate")
    @Bulkhead(name = "activityService")
    @PostMapping
    public ResponseEntity<ActivityResponse> create(@Valid @RequestBody ActivityRequest dto) {
        // 🔥 Simular falha controlada
        if ("FAIL".equalsIgnoreCase(dto.getName())) {
            throw new RuntimeException("Falha simulada para teste de Resilience4j.");
        }

        Activity activity = activityMapper.toDomain(dto);
        Activity created = createActivityService.create(activity);
        if (created == null) {
            return ResponseEntity.badRequest().build();
        }

        URI location = URI.create(String.format("/v1/activitys/%s", created.getId()));
        ActivityResponse response = activityMapper.toResponse(created);
        return ResponseEntity.created(location).body(response);
    }

    // 🔎 Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<ActivityResponse> findById(@PathVariable UUID id) {
        Activity activityById = getActivityService.findById(id);
        if (activityById != null) {
            ActivityResponse byId = activityMapper.toResponse(activityById);
            return ResponseEntity.ok(byId);
        }

        return ResponseEntity.notFound().build();
    }

    // 🔎 Listar todos
    @GetMapping
    public List<ActivityResponse> listAll() {
        List<Activity> cours = getActivityService.findAll();
        return cours.stream()
                .map(activityMapper::toResponse)
                .toList();
    }

    // 🔄 Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<ActivityResponse> update(@PathVariable UUID id, @Valid @RequestBody ActivityRequest dto) {
        Activity activity = activityMapper.toDomain(dto);
        Activity update = updateActivityService.update(id, activity);
        if (update != null) {
            ActivityResponse response = activityMapper.toResponse(update);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    // ❌ Deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (deleteActivityService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<ActivityResponse> fallbackCreate(ActivityRequest dto, Throwable t) {
        Activity fallback = new Activity();
        fallback.setId(UUID.randomUUID());
        fallback.setDescription("Fallback description");
        fallback.setPoint(0);
        fallback.setExecuteDate(null);
        fallback.setAvailabilityDate(null);
        fallback.setActivityType(ActivityType.VIDEO);
        fallback.setActivityPath("Fallback path");

        ActivityResponse response = activityMapper.toResponse(fallback);

        if (t instanceof BulkheadFullException || t instanceof CallNotPermittedException) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
