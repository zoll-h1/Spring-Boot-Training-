package zoll_h1.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zoll_h1.training.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
