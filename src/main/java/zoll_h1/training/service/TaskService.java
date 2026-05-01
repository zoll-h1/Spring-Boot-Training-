package zoll_h1.training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zoll_h1.training.dto.TaskRequestDTO;
import zoll_h1.training.dto.TaskResponseDTO;
import zoll_h1.training.exception.ResourceNotFoundException;
import zoll_h1.training.model.Project;
import zoll_h1.training.model.Task;
import zoll_h1.training.model.User;
import zoll_h1.training.repository.ProjectRepository;
import zoll_h1.training.repository.TaskRepository;
import zoll_h1.training.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        Task task = mapToEntity(taskRequestDTO);
        Task saved = taskRepository.save(task);
        return mapToDTO(saved);
    }

    public List<TaskResponseDTO> getAllTasks() {
       return taskRepository.findAll()
               .stream()
               .map(this::mapToDTO)
               .collect(Collectors.toList());
    }

    public TaskResponseDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        return mapToDTO(task);
    }

    public TaskResponseDTO updateTask(Long id, TaskRequestDTO updatedTask) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found task with id: " + id));
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());
        Task saved = taskRepository.save(task);
        return mapToDTO(saved);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public TaskResponseDTO createAndAssignTask(Task task, Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Not found a project with id: " + projectId));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Not found user with id: " + userId));
        task.setProject(project);
        task.setUser(user);
        taskRepository.save(task);
        return mapToDTO(task);
    }
    private TaskResponseDTO mapToDTO(Task task) {
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());

        if(task.getProject() != null) {
            dto.setProjectName(task.getProject().getName());
        }
        if(task.getUser() != null) {
            dto.setAssignedUsername(task.getUser().getUsername());
        }
        return dto;
    }
    private Task mapToEntity(TaskRequestDTO requestDTO) {
        Task task = new Task();
        task.setTitle(requestDTO.getTitle());
        task.setDescription(requestDTO.getDescription());
        task.setStatus(requestDTO.getStatus());
        return task;
    }

}
