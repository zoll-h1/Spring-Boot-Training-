package zoll_h1.training.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zoll_h1.training.dto.TaskRequestDTO;
import zoll_h1.training.dto.TaskResponseDTO;
import zoll_h1.training.exception.ResourceNotFoundException;
import zoll_h1.training.model.Task;
import zoll_h1.training.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        List<TaskResponseDTO> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        TaskResponseDTO taskResponseDTO = taskService.getTaskById(id);
        return ResponseEntity.ok(taskResponseDTO);
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO createdDTO = taskService.createTask(taskRequestDTO);
        return ResponseEntity.ok(createdDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequestDTO updateTaskDetails) {
        TaskResponseDTO updated = taskService.updateTask(id, updateTaskDetails);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/assign/{projectId}/{userId}")
    public ResponseEntity<TaskResponseDTO> createAndAssign(@RequestBody Task task, @PathVariable Long projectId , @PathVariable Long userId) {
        TaskResponseDTO savedTask = taskService.createAndAssignTask(task, projectId, userId);
        return ResponseEntity.ok(savedTask);
    }
}
