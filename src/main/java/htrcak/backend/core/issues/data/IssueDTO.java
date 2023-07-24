package htrcak.backend.core.issues.data;

import htrcak.backend.core.issues.Issue;
import htrcak.backend.core.label.data.LabelDTO;
import htrcak.backend.core.user.model.User;
import htrcak.backend.core.user.model.UserDTO;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class IssueDTO {

    private long id;

    private long projectId;

    private String title;

    private LocalDateTime created;

    private LocalDateTime edited;

    private int commentNumber;

    private String issueType;

    private UserDTO originalPoster;

    private boolean isOpened;

    private Set<LabelDTO> labels;

    public IssueDTO(Issue issue) {
        this.id = issue.getId();
        this.title = issue.getTitle();
        this.commentNumber = issue.getCommentNumber();
        this.issueType = issue.getIssueType();
        this.projectId = issue.getProject().getId();
        this.originalPoster = new UserDTO(issue.getOriginalPoster());
        this.isOpened = issue.isOpened();
        this.created = issue.getCreated();
        this.edited = issue.getEdited();
        this.labels = issue.getLabelsSet().stream().map(LabelDTO::new).collect(Collectors.toSet());
    }

    public long getId() {
        return id;
    }

    public long getProjectId() {
        return projectId;
    }

    public String getTitle() {
        return title;
    }

    public int getCommentNumber() {
        return commentNumber;
    }

    public String getIssueType() {
        return issueType;
    }

    public UserDTO getOriginalPoster() {
        return originalPoster;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getEdited() {
        return edited;
    }

    public Set<LabelDTO> getLabels() {
        return labels;
    }
}
