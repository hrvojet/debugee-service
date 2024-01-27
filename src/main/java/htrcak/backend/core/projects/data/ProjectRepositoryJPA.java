package htrcak.backend.core.projects.data;

import htrcak.backend.core.projects.Project;
import htrcak.backend.core.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface ProjectRepositoryJPA extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

    List<Project> findAllByOwner(User user);

    Project getById(long id);

    Project getReferenceById(Class<Project> projectClass, Long id);
}
