package htrcak.backend.core.issues.data;

public class IssueDTO {

    private long id;

    private long projectId;

    private String title;

    private int commentNumber;

    private String issueType;

    public IssueDTO(long id, long projectId, String title, int commentNumber, String issueType) {
        this.id = id;
        this.title = title;
        this.commentNumber = commentNumber;
        this.issueType = issueType;
        this.projectId = projectId;
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
}
