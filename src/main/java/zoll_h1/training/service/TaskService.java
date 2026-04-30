package zoll_h1.training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zoll_h1.training.model.Project;
import zoll_h1.training.model.Task;
import zoll_h1.training.model.User;
import zoll_h1.training.repository.ProjectRepository;
import zoll_h1.training.repository.TaskRepository;
import zoll_h1.training.repository.UserRepository;

import java.util.List;
import java.util.Optional;

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

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id).map(existingTask -> {
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setStatus(updatedTask.getStatus());
            return taskRepository.save(existingTask);
        }).orElseThrow(() -> new RuntimeException("Task not found by id: " + id));
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Task createAndAssignTask(Task task, Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Not found a project with id: " + projectId));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Not found user with id: " + userId));
        task.setProject(project);
        task.setUser(user);
        return taskRepository.save(task);
    }

}
