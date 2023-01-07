package htrcak.backend.projects;

import htrcak.backend.projects.data.ProjectDTO;
import htrcak.backend.projects.data.ProjectPost;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    List<ProjectDTO> findAll();

    ProjectDTO findById(long id);

    Optional<ProjectDTO> saveNewProject(ProjectPost projectPost);
}
