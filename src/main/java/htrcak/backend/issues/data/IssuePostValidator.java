package htrcak.backend.issues.data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class IssuePostValidator {

    @NotBlank(message = "Title must not be empty")
    private String title;

    @NotBlank(message = "Description must not be empty")
    private String firstComment;

    private String issueType;

    public String getTitle() {
        return title;
    }

    public String getFirstComment() {
        return firstComment;
    }

    public String getIssueType() {
        return issueType;
    }
}
