package htrcak.backend.repository.dto;

public class IssueDTO {

    private long id;

    private String title;

    private int commentNumber;

    private String issueType;

    public IssueDTO(long id, String title, int commentNumber, String issueType) {
        this.id = id;
        this.title = title;
        this.commentNumber = commentNumber;
        this.issueType = issueType;
    }

    public long getId() {
        return id;
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
