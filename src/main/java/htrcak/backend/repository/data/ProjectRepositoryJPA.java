package htrcak.backend.repository.data;

import htrcak.backend.repository.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepositoryJPA extends JpaRepository<Project, Long> {

    List<Project> findAll();

    List<Project> findProjectById(long id);

}
