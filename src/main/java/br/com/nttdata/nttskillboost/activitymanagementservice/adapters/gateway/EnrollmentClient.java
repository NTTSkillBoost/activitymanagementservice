package br.com.nttdata.nttskillboost.activitymanagementservice.adapters.gateway;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.UUID;

@Component
public class EnrollmentClient {

    private final WebClient webClient;

    public EnrollmentClient(WebClient.Builder builder) {
        //this.webClient = builder.baseUrl("http://enrollment-service/api/v1/enrollments").build();
        this.webClient = builder.baseUrl("http://localhost:8885/api/v1/enrollments").build();
    }

    @Retry(name = "enrollmentService")
    @CircuitBreaker(name = "enrollmentService", fallbackMethod = "fallbackEnrollmentCheck")
    public boolean isStudentEnrolled(UUID studentId, UUID courseId) {
        try {
            webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/exists")
                            .queryParam("studentId", studentId)
                            .queryParam("courseId", courseId)
                            .build())
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            return true;
        } catch (WebClientResponseException.NotFound e) {
            return false;
        }
    }

    public boolean fallbackEnrollmentCheck(UUID studentId, UUID courseId, Throwable t) {
        // Decide se bloqueia a atividade ou permite sem validar (recomendo bloquear)
        return false;
    }
}
/*ActivityManagementService → GET /api/v1/enrollments/exists?studentId=xxx&courseId=yyy
→ 200 OK → aluno matriculado
→ 404 Not Found → aluno não matriculado*/
