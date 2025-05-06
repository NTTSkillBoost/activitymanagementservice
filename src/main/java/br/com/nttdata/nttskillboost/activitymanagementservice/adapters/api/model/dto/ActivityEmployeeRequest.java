package br.com.nttdata.nttskillboost.activitymanagementservice.adapters.api.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityEmployeeRequest {

    @Schema(description = "Nome do funcionário", example = "João da Silva")
    @NotBlank(message = "courseId não pode ser nulo")
    private UUID activityId;

    @Schema(description = "Nome do funcionário", example = "João da Silva")
    @NotBlank(message = "courseId não pode ser nulo")
    private UUID employeeId;

    @Schema(description = "Descrição do funcionário", example = "123.456.789-00")
    private String description;

    @Schema(description = "point", example = "40")
    private Integer percentageConcluded;

    @Schema(description = "ID do Edereço no sistema de terceiros", example = "123e4567-e89b-12d3-a456-426614174000")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate concludedDate;
}
