package htrcak.backend.core.projects;

import htrcak.backend.core.projects.data.ProjectDTO;
import htrcak.backend.core.projects.data.ProjectPatchValidator;
import htrcak.backend.core.projects.data.ProjectPostValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public Page<ProjectDTO> findAllProjects(
            @RequestParam(required = false, defaultValue = "0") final int page,
            @RequestParam(required = false, defaultValue = "5") final int size,
            @RequestParam(required = false, defaultValue = "ASC") final String sortBy,
            @RequestParam(required = false, defaultValue = "id") final String id) {
        return projectService.findAll(PageRequest.of(page, size, Sort.by("ASC".equalsIgnoreCase(sortBy) ? Sort.Direction.ASC : Sort.Direction.DESC, id)));
    }

    @GetMapping("/{id}")
    public ProjectDTO findProjectById(@PathVariable final Long id) {
        return projectService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> saveProject(@Valid @RequestBody final ProjectPostValidator projectPost) {
        return projectService.saveNewProject(projectPost);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteProject(@PathVariable long id) {
        return projectService.deleteById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProject(@Valid @RequestBody final ProjectPatchValidator projectPost, @PathVariable final long id) {
        return projectService.updateById(projectPost, id);
    }

}
