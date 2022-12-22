package htrcak.backend.projects;

import htrcak.backend.projects.data.ProjectDTO;

import java.util.List;

public interface ProjectService {

    List<ProjectDTO> findAll();

    ProjectDTO findById(long id);
}
