package htrcak.backend.core.projects;

import htrcak.backend.core.projects.data.ProjectDTO;
import htrcak.backend.core.projects.data.ProjectPatchValidator;
import htrcak.backend.core.projects.data.ProjectPostValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    Page<ProjectDTO> findAll(Pageable pageable, String searchProjectByTitle);

    Page<ProjectDTO> findAllFavourites(Pageable pageable, String searchProjectByTitle);

    ProjectDTO findById(long id);

    ResponseEntity<ProjectDTO> saveNewProject(ProjectPostValidator projectPost);

    ResponseEntity<?> deleteById(long id);

    ResponseEntity<?> updateById(ProjectPatchValidator projectPost, long id);

    ResponseEntity<?> addProjectToFavourites(Long projectId);

    ResponseEntity<?> removeProjectFromFavourites(Long projectId);

    ResponseEntity<String> getBadgeForProject(Long projectId);
}
