package htrcak.backend.services;

import htrcak.backend.repository.dto.ProjectDTO;
import htrcak.backend.repository.models.Project;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProjectService {

    List<ProjectDTO> findAll();

    ProjectDTO findById(long id);
}
