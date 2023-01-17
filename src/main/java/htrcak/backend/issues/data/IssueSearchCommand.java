package htrcak.backend.issues.data;


public class IssueSearchCommand {

    private String title;

    private String issueType; // TODO search by issue type (enum)

    public String getTitle() {
        return title;
    }

    public String getIssueType() {
        return issueType;
    }
}
