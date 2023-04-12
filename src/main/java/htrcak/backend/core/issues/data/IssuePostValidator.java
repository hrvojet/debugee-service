package htrcak.backend.core.issues.data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class IssuePostValidator {

    @NotBlank(message = "Title must not be empty")
    private String title;

    @NotBlank(message = "Description must not be empty")
    private String firstComment;

    @NotNull(message = "Project ID must nut be empty")
    private Long projectId;

    private String issueType;

    public String getTitle() {
        return title;
    }

    public String getFirstComment() {
        return firstComment;
    }

    public Long getProjectId() {
        return projectId;
    }

    public String getIssueType() {
        return issueType;
    }
}
