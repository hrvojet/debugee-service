package htrcak.backend.core.projects;

import htrcak.backend.core.projects.data.ProjectDTO;
import htrcak.backend.core.projects.data.ProjectPatchValidator;
import htrcak.backend.core.projects.data.ProjectPostValidator;
import htrcak.backend.core.projects.data.ProjectRepositoryJPA;
import htrcak.backend.core.user.model.User;
import htrcak.backend.utils.SecurityContextHolderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

import static htrcak.backend.utils.jpa.ProjectSpecification.favouriteByUser;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class ProjectServiceImpl implements ProjectService{

    Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    private final ProjectRepositoryJPA projectRepositoryJPA;
    private final SecurityContextHolderUtils securityContextHolderUtils;

    public ProjectServiceImpl(ProjectRepositoryJPA projectRepositoryJPA, SecurityContextHolderUtils securityContextHolderUtils) {
        this.projectRepositoryJPA = projectRepositoryJPA;
        this.securityContextHolderUtils = securityContextHolderUtils;
    }


    @Override
    public Page<ProjectDTO> findAll(Pageable pageable) {
        return projectRepositoryJPA.findAll(pageable).map(this::mapProjectToDTO);
    }

    @Override
    public Page<ProjectDTO> findAllFavourites(Pageable pageable) {

        User user = securityContextHolderUtils.getCurrentUser();

        return projectRepositoryJPA.findAll(where(favouriteByUser(user.getId())), pageable)
                .map(this::mapProjectToDTO);
    }


    @Override
    public ProjectDTO findById(long id) {
        return projectRepositoryJPA.findById(id).stream().map(this::mapProjectToDTO).findAny().orElse(null);
    }

    @Override
    public ResponseEntity<ProjectDTO> saveNewProject(ProjectPostValidator projectPost) {
        Project saved = this.projectRepositoryJPA.save(new Project(
                projectPost.getTitle(),
                projectPost.getDescription(),
                0,
                0,
                securityContextHolderUtils.getCurrentUser()));
            return new ResponseEntity<>(mapProjectToDTO(saved), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> deleteById(long id) {

        long projectForDeletionOwnerId = this.projectRepositoryJPA.getById(id).getOwner().getId();
        if (projectForDeletionOwnerId == securityContextHolderUtils.getCurrentUser().getId() || securityContextHolderUtils.getCurrentUser().isAdmin()) {
            this.projectRepositoryJPA.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        logger.warn(MessageFormat.format("User with id {0} not allowed to delete project with id {1}", securityContextHolderUtils.getCurrentUser().getId(), id));
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<?> updateById(ProjectPatchValidator projectPatchValidator, long id) {
        Optional<Project> project = this.projectRepositoryJPA.findById(id);
        if (project.isEmpty()) {
            logger.warn(MessageFormat.format("There is no project with id [{0}]", id));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (project.get().getOwner().getId() != securityContextHolderUtils.getCurrentUser().getId()) {
            logger.warn(MessageFormat.format("User with id [{0}] not allowed to modify project with id [{1}]", securityContextHolderUtils.getCurrentUser().getId(), id));
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        boolean projectIsUpdated = false;
        if (projectPatchValidator.getDescription() != null && !projectPatchValidator.getDescription().isBlank() && !projectPatchValidator.getDescription().equals(project.get().getDescription())) {
            project.get().setDescription(projectPatchValidator.getDescription());
            projectIsUpdated = true;
        }
        if (projectPatchValidator.getTitle() != null && !projectPatchValidator.getTitle().isBlank() && !projectPatchValidator.getTitle().equals(project.get().getTitle())) {
            project.get().setTitle(projectPatchValidator.getTitle());
            projectIsUpdated = true;
        }

        if (projectIsUpdated) {
            return new ResponseEntity<>(mapProjectToDTO(this.projectRepositoryJPA.save(project.get())), HttpStatus.OK);
        } else {
            logger.debug(MessageFormat.format("No change applied to project with {0} ID", id));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity<?> addProjectToFavourites(Long projectId) {
        Optional<Project> project = this.projectRepositoryJPA.findById(projectId);
        if (project.isEmpty()) {
            logger.warn(MessageFormat.format("There is no project with id [{0}]", projectId));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = securityContextHolderUtils.getCurrentUser();
        project.get().addProjectAsFavourite(user);

        return new ResponseEntity<>(mapProjectToDTO(this.projectRepositoryJPA.save(project.get())), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> removeProjectFromFavourites(Long projectId) {
        Optional<Project> project = this.projectRepositoryJPA.findById(projectId);
        if (project.isEmpty()) {
            logger.warn(MessageFormat.format("There is no project with id [{0}]", projectId));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = securityContextHolderUtils.getCurrentUser();
        project.get().removeProjectAsFavourite(user);

        return new ResponseEntity<>(mapProjectToDTO(this.projectRepositoryJPA.save(project.get())), HttpStatus.OK);
    }

    private ProjectDTO mapProjectToDTO(Project project) {
        return new ProjectDTO(project);
    }

}
