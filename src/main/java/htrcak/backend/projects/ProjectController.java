package htrcak.backend.projects;

import htrcak.backend.projects.data.ProjectDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
