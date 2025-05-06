package br.com.nttdata.nttskillboost.activitymanagementservice.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_activity_employee")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Activity Employee Entity", description = "Operações relacionadas a o funcionário")
public class ActivityEmployee extends AuditDomain {

    @Schema(description = "ID do Employee", example = "123e4567-e89b-12d3-a456-426614174000")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Schema(description = "Nome do funcionário", example = "João da Silva")
    @Column(name = "activity_id")
    private UUID activityId;

    @Schema(description = "Nome do funcionário", example = "João da Silva")
    @Column(name = "employee_id")
    private UUID employeeId;

    @Schema(description = "Descrição do funcionário", example = "123.456.789-00")
    private String description;

    @Schema(description = "percentage_concluded", example = "40")
    @Column(name = "percentage_concluded")
    private Integer percentageConcluded;

    @Schema(description = "ID do Edereço no sistema de terceiros", example = "123e4567-e89b-12d3-a456-426614174000")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "concluded_date")
    private LocalDate concludedDate;
}