package br.com.nttdata.nttskillboost.activitymanagementservice;

import br.com.nttdata.nttskillboost.activitymanagementservice.adapters.api.model.dto.ActivityRequest;
import br.com.nttdata.nttskillboost.activitymanagementservice.adapters.api.model.dto.ActivityResponse;
import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.Activity;
import br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity.ActivityType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ActivityControllerResilienceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testRetryAndFallback() {
        int random = new Random().nextInt(1000);
        ActivityRequest request = ActivityRequest.builder()
                .courseId(UUID.randomUUID())
                .description("Concurrent " + random)
                .point(random)
                .executeDate(LocalDate.now())
                .availabilityDate(LocalDateTime.now())
                .activityType(ActivityType.VIDEO.getType())
                .activityPath("Path " + random)
                .build();

        ResponseEntity<ActivityResponse> response = restTemplate.postForEntity(
                "http://localhost:8881/api/v1/courses", request, ActivityResponse.class);

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        // Mockito.verify não funciona em chamada REST com o contexto real
    }

    @Test
    void testBulkheadWithConcurrentRequests() throws InterruptedException {
        int concurrentRequests = 15; // bulkhead está configurado para aceitar até 10
        ExecutorService executor = Executors.newFixedThreadPool(concurrentRequests);

        CountDownLatch latch = new CountDownLatch(concurrentRequests);

        for (int i = 0; i < concurrentRequests; i++) {
            int requestNumber = i + 1;
            executor.submit(() -> {
                try {
                    Activity request = Activity.builder()
                            .courseId(UUID.randomUUID())
                            .description("Concurrent " + requestNumber)
                            .point(requestNumber)
                            .executeDate(LocalDate.now())
                            .availabilityDate(LocalDateTime.now())
                            .activityType(ActivityType.VIDEO)
                            .activityPath("Path " + requestNumber)
                            .build();

                    ResponseEntity<String> response = restTemplate.postForEntity(
                            "http://localhost:8881/api/v1/courses", request, String.class);

                    if (response.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
                        System.out.println("Erro retornado pela API: " + response.getBody());
                    } else if (response.getStatusCode().is2xxSuccessful()) {
                        try {
                            ActivityResponse courseResponse = new ObjectMapper().readValue(response.getBody(), ActivityResponse.class);
                            System.out.println("Resposta bem-sucedida: " + courseResponse);
                        } catch (JsonProcessingException e) {
                            System.out.println("Erro ao processar JSON: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Resposta inesperada: " + response.getBody());
                    }

                    System.out.println("Request " + requestNumber + " status: " + response.getStatusCode());
                } catch (Exception e) {
                    System.out.println("Request " + requestNumber + " failed: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // Aguarda todas as requisições terminarem
        executor.shutdown();
    }
}