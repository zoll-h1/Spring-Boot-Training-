package zoll_h1.training.dto;

import lombok.Data;

@Data
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String status;

    private String projectName;
    private String assignedUsername;
}
