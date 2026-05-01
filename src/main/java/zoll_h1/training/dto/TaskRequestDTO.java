package zoll_h1.training.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskRequestDTO {

    @NotBlank(message = "Task title can not be empty")
    @Size(min = 3, max = 50, message = "Description have to be less than 50 more than 3")
    private String description;

    @NotBlank(message = "Title is required")
    private String title;

    @Pattern(regexp = "^(TODO|IN_PROGRESS|DONE)$", message = "Status must be TODO|IN_PROGRESS|DONE")
    private String status;
}
