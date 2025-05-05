package br.com.nttdata.nttskillboost.activitymanagementservice.adapters.api.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ActivityRequest {

    @Schema(description = "Nome do funcionário", example = "João da Silva")
    private String name;

    @Schema(description = "Nome do funcionário", example = "João da Silva")
    @NotBlank(message = "courseId não pode ser nulo")
    private UUID courseId;

    @Schema(description = "Descrição do funcionário", example = "123.456.789-00")
    private String description;

    @Schema(description = "point", example = "40")
    private Integer point;

    @Schema(description = "ID do Edereço no sistema de terceiros", example = "123e4567-e89b-12d3-a456-426614174000")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate executeDate;

    @Schema(description = "ID do Edereço no sistema de terceiros", example = "2023-10-01 T10:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime availabilityDate;

    @Schema(description = "ID do Edereço no sistema de terceiros", example = "123e4567-e89b-12d3-a456-426614174000")
    private String activityType;

    @Schema(description = "ID do Edereço no sistema de terceiros", example = "123e4567-e89b-12d3-a456-426614174000")
    private String activityPath;
}
