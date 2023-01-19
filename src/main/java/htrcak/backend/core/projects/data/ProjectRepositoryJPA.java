package htrcak.backend.core.projects.data;

import htrcak.backend.core.projects.Project;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProjectRepositoryJPA extends JpaRepository<Project, Long> {

}
