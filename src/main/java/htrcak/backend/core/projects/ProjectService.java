package htrcak.backend.core.projects;

import htrcak.backend.core.projects.data.ProjectDTO;
import htrcak.backend.core.projects.data.ProjectPatchValidator;
import htrcak.backend.core.projects.data.ProjectPostValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    Page<ProjectDTO> findAll(Pageable pageable);

    ProjectDTO findById(long id);

    ResponseEntity<ProjectDTO> saveNewProject(ProjectPostValidator projectPost);

    ResponseEntity<?> deleteById(long id);

    ResponseEntity<?> updateById(ProjectPatchValidator projectPost, long id);
}
