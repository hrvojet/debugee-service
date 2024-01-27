package htrcak.backend.core.label;

import htrcak.backend.core.issues.Issue;
import htrcak.backend.core.issues.data.IssueRepositoryJPA;
import htrcak.backend.core.label.data.*;
import htrcak.backend.core.projects.Project;
import htrcak.backend.core.projects.data.ProjectRepositoryJPA;
import htrcak.backend.exceptions.UserNotAllowedException;
import htrcak.backend.utils.SecurityContextHolderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LabelServiceImpl implements LabelService {

    Logger logger = LoggerFactory.getLogger(LabelServiceImpl.class);

    private final LabelRepositoryJPA labelRepositoryJPA;
    private final IssueRepositoryJPA issueRepositoryJPA;
    private final ProjectRepositoryJPA projectRepositoryJPA;
    private final SecurityContextHolderUtils securityContextHolderUtils;

    public LabelServiceImpl(LabelRepositoryJPA labelRepositoryJPA, IssueRepositoryJPA issueRepositoryJPA, ProjectRepositoryJPA projectRepositoryJPA, SecurityContextHolderUtils securityContextHolderUtils) {
        this.labelRepositoryJPA = labelRepositoryJPA;
        this.issueRepositoryJPA = issueRepositoryJPA;
        this.projectRepositoryJPA = projectRepositoryJPA;
        this.securityContextHolderUtils = securityContextHolderUtils;
    }

    @Override
    public ResponseEntity<List<LabelDTO>> getLabelsForProject(Long projectID) {
        Project p = projectRepositoryJPA.getReferenceById(Project.class, projectID);

        return new ResponseEntity<>(labelRepositoryJPA.findAllByProject(p).stream().map(LabelDTO::new).collect(Collectors.toList()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<LabelDTO> saveNewLabel(Long projectID, LabelPostValidator labelPostValidator) {
        Project p = projectRepositoryJPA.getReferenceById(Project.class, projectID);

        checkIfUserIsAllowed(p);

        List<Label> labelsList = labelRepositoryJPA.findAllByProject(p);

        Label labelWithSameName = labelsList.stream()
                .filter(label -> label.getName().equalsIgnoreCase(labelPostValidator.getName()))
                .findFirst()
                .orElse(null);

        if(labelWithSameName != null) {
            logger.warn(MessageFormat.format("Label with name \"{0}\" already exists", labelPostValidator.getName()));
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            Label newLabel = labelRepositoryJPA.save(new Label(labelPostValidator, p));
            return new ResponseEntity<>(new LabelDTO(newLabel), HttpStatus.ACCEPTED);
        }
    }

    @Override
    public ResponseEntity<?> addLabelToIssue(Long issueID, Long labelID) {
        // prosljedi ID issua i labela, al u servisu provjeri dal oboje pripadaju istom projektu
        Issue issue = issueRepositoryJPA.getById(issueID);
        Label label = labelRepositoryJPA.getById(labelID);

        checkIfUserIsAllowed(issue);

        if (issue.getProject().getId() == label.getProject().getId()) {
            issue.addLabel(label);
            issueRepositoryJPA.save(issue);
            return new ResponseEntity<>(new LabelDTO(label), HttpStatus.ACCEPTED);
        } else {
            logger.warn(MessageFormat.format("Label with ID \"{0}\" and issue with ID \"{1}\" do not belong to same project", label.getId(), issue.getId()));
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public ResponseEntity<?> removeLabelFromIssue(Long issueID, Long labelID) {
        Issue issue = issueRepositoryJPA.getById(issueID);
        Label label = labelRepositoryJPA.getById(labelID);

        checkIfUserIsAllowed(issue);

        if (issue.getProject().getId() == label.getProject().getId()) {
            issue.removeLabel(label);
            issueRepositoryJPA.save(issue);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            logger.warn(MessageFormat.format("Label with ID \"{0}\" and issue with ID \"{1}\" do not belong to same project", label.getId(), issue.getId()));
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public ResponseEntity<LabelDTO> editLabel(Long labelID, LabelPatchValidator labelPatchValidator) {
        Optional<Label> label = labelRepositoryJPA.findById(labelID);
        if (label.isEmpty()) {
            logger.warn(MessageFormat.format("There is no label with id [{0}]", labelID));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Project project = label.get().getProject();

        checkIfUserIsAllowed(project);

        if (project.getId() != label.get().getProject().getId()) {
            logger.warn(MessageFormat.format("Label with ID \"{0}\" and issue with ID \"{1}\" do not belong to same project", label.get().getId(), project.getId()));
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        boolean labelIsUpdated = false;
        if (labelPatchValidator.getDescription() != null && !labelPatchValidator.getDescription().isBlank() && !labelPatchValidator.getDescription().equals(label.get().getDescription())) {
            label.get().setDescription(labelPatchValidator.getDescription());
            labelIsUpdated = true;
        }
        if (labelPatchValidator.getName() != null && !labelPatchValidator.getName().isBlank() && !labelPatchValidator.getName().equals(label.get().getName())) {
            label.get().setName(labelPatchValidator.getName());
            labelIsUpdated = true;
        }
        if (labelPatchValidator.getColorHex() != null && !labelPatchValidator.getColorHex().isBlank() && !labelPatchValidator.getColorHex().equals(label.get().getColorHex())) {
            label.get().setColorHex(labelPatchValidator.getColorHex());
            labelIsUpdated = true;
        }

        if (labelIsUpdated) {
            return new ResponseEntity<>(new LabelDTO(labelRepositoryJPA.save(label.get())), HttpStatus.ACCEPTED);
        } else {
            logger.debug(MessageFormat.format("No change applied to label with {0} ID", labelID));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity<?> deleteLabel(Long labelID) {
        Optional<Label> label = labelRepositoryJPA.findById(labelID);
        if (label.isEmpty()) {
            logger.warn(MessageFormat.format("There is no label with id [{0}]", labelID));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        checkIfUserIsAllowed(label.get().getProject());

        labelRepositoryJPA.delete(label.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<HttpStatus> updateLabelsForIssue(long issueID, LabelUpdatePostValidator labelUpdatePostValidator) {

        Issue issueToUpdate = issueRepositoryJPA.getById(issueID);

        checkIfUserIsAllowed(issueToUpdate);

        for (Long labelID : labelUpdatePostValidator.getAddLabelsWithID()) {
            Label tmpLabel = labelRepositoryJPA.getById(labelID);
            issueToUpdate.addLabel(tmpLabel);
        }

        for (Long labelID : labelUpdatePostValidator.getRemoveLabelsWithID()) {
            Label tmpLabel = labelRepositoryJPA.getById(labelID);
            issueToUpdate.removeLabel(tmpLabel);
        }

        if (!labelUpdatePostValidator.getRemoveLabelsWithID().isEmpty() || !labelUpdatePostValidator.getAddLabelsWithID().isEmpty()) {
            issueRepositoryJPA.save(issueToUpdate);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private <T> void checkIfUserIsAllowed(T t) {
        // TODO util class?
        if (t instanceof Issue) {
            if (((Issue) t).getProject().getOwner().getId() != securityContextHolderUtils.getCurrentUser().getId()) {
                logger.warn(MessageFormat.format("User with id [{0}] not allowed to modify issue with id [{1}]", securityContextHolderUtils.getCurrentUser().getId(), ((Issue) t).getProject().getId()));
                throw new UserNotAllowedException();
            }
        } else if (t instanceof Project) {
            if (((Project) t).getOwner().getId() != securityContextHolderUtils.getCurrentUser().getId()) {
                logger.warn(MessageFormat.format("User with id [{0}] not allowed to modify project with id [{1}]", securityContextHolderUtils.getCurrentUser().getId(), ((Project) t).getId()));
                throw new UserNotAllowedException();
            }
        }
    }
}
