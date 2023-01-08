package htrcak.backend.projects;

import htrcak.backend.projects.data.ProjectDTO;
import htrcak.backend.projects.data.ProjectPostValidator;
import org.springframework.http.HttpStatus;
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
    public List<ProjectDTO> findAllProjects() { return projectService.findAll(); }

    @GetMapping("/{id}")
    public ProjectDTO findProjectById(@PathVariable final Long id) {
        return projectService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> saveProject(@Valid @RequestBody final ProjectPostValidator projectPost) {
        return projectService.saveNewProject(projectPost)
                .map(
                        ProjectDTO -> ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(ProjectDTO)
                ).orElseGet(
                        () -> ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .build()
                );
    }

}
