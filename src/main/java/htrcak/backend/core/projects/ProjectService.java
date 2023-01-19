package htrcak.backend.core.projects;

import htrcak.backend.core.projects.data.ProjectDTO;
import htrcak.backend.core.projects.data.ProjectPatchValidator;
import htrcak.backend.core.projects.data.ProjectPostValidator;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    List<ProjectDTO> findAll();

    ProjectDTO findById(long id);

    Optional<ProjectDTO> saveNewProject(ProjectPostValidator projectPost);

    void deleteById(long id);

    Optional<ProjectDTO> updateById(ProjectPatchValidator projectPost, long id);
}
