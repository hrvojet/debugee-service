package htrcak.backend.projects.data;

import htrcak.backend.projects.Project;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProjectRepositoryJPA extends JpaRepository<Project, Long> {

}
