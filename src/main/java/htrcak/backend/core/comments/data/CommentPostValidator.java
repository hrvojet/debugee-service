package htrcak.backend.core.comments.data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CommentPostValidator {

    @NotNull(message = "Issue ID must not be empty")
    private long issueId;

    @NotBlank(message = "Text must not be empty")
    private String text;


    public long getIssueId() {
        return issueId;
    }


    public String getText() {
        return text;
    }
}
