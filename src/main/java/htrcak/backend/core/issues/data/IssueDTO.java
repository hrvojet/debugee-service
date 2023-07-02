package htrcak.backend.core.issues.data;

import htrcak.backend.core.issues.Issue;
import htrcak.backend.core.user.model.User;
import htrcak.backend.core.user.model.UserDTO;

public class IssueDTO {

    private long id;

    private long projectId;

    private String title;

    private int commentNumber;

    private String issueType;

    private UserDTO originalPoster;

    private boolean isOpened;

    public IssueDTO(Issue issue) {
        this.id = issue.getId();
        this.title = issue.getTitle();
        this.commentNumber = issue.getCommentNumber();
        this.issueType = issue.getIssueType();
        this.projectId = issue.getProject().getId();
        this.originalPoster = new UserDTO(issue.getOriginalPoster());
        this.isOpened = issue.isOpened();
    }

    public IssueDTO(long id, long projectId, String title, int commentNumber, String issueType, User originalPoster, boolean isOpened) {
        this.id = id;
        this.title = title;
        this.commentNumber = commentNumber;
        this.issueType = issueType;
        this.projectId = projectId;
        this.originalPoster = new UserDTO(
                originalPoster.getId(),
                originalPoster.getName(),
                originalPoster.getEmail(),
                originalPoster.getAvatarUrl(),
                originalPoster.getWebUrl()
        );
        this.isOpened = isOpened;
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
}
