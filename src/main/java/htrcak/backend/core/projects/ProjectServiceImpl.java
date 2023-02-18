package htrcak.backend.core.projects;

import htrcak.backend.core.projects.data.ProjectDTO;
import htrcak.backend.core.projects.data.ProjectPatchValidator;
import htrcak.backend.core.projects.data.ProjectPostValidator;
import htrcak.backend.core.projects.data.ProjectRepositoryJPA;
import htrcak.backend.utils.SecurityContextHolderUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepositoryJPA projectRepositoryJPA;
    private final SecurityContextHolderUtils securityContextHolderUtils;

    public ProjectServiceImpl(ProjectRepositoryJPA projectRepositoryJPA, SecurityContextHolderUtils securityContextHolderUtils) {
        this.projectRepositoryJPA = projectRepositoryJPA;
        this.securityContextHolderUtils = securityContextHolderUtils;
    }


    @Override
    public List<ProjectDTO> findAll() {
        return projectRepositoryJPA.findAll().stream().map(this::mapProjectToDTO).collect(Collectors.toList());
    }

    @Override
    public ProjectDTO findById(long id) {
        return projectRepositoryJPA.findById(id).stream().map(this::mapProjectToDTO).findAny().orElse(null);
    }

    @Override
    public Optional<ProjectDTO> saveNewProject(ProjectPostValidator projectPost) {
        Project saved = this.projectRepositoryJPA.save(new Project(
                projectPost.getTitle(),
                projectPost.getDescription(),
                0,
                0,
                securityContextHolderUtils.getCurrentUser()));

        return Optional.of(mapProjectToDTO(saved));
    }

    @Override
    public void deleteById(long id) {
        this.projectRepositoryJPA.deleteById(id);
    }

    @Override
    public Optional<ProjectDTO> updateById(ProjectPatchValidator projectPatchValidator, long id) {
        Project p = this.projectRepositoryJPA.getById(id);
        boolean projectIsUpdated = false;

        if(projectPatchValidator.getDescription() != null && !projectPatchValidator.getDescription().isBlank() && !projectPatchValidator.getDescription().equals(p.getDescription())) {
            p.setDescription(projectPatchValidator.getDescription());
            projectIsUpdated = true;
        }
        if(projectPatchValidator.getTitle() != null && !projectPatchValidator.getTitle().isBlank() && !projectPatchValidator.getTitle().equals(p.getTitle())) {
            p.setTitle(projectPatchValidator.getTitle());
            projectIsUpdated = true;
        }

        if(projectIsUpdated) {
            return Optional.of(mapProjectToDTO(this.projectRepositoryJPA.save(p)));
        } else {
            return Optional.empty();
        }
    }

    private ProjectDTO mapProjectToDTO(Project project) {
        return new ProjectDTO(project.getId(), project.getTitle(), project.getDescription(), project.getOpenedIssues(), project.getClosedIssues(), project.getOwner());
    }

}
