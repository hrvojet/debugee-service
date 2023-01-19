package htrcak.backend.core.comments.data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CommentPostValidator {

    @NotNull(message = "Issue ID must not be empty")
    private long issueId;

    @NotNull(message = "User ID must not be empty")
    private long userId;

    @NotBlank(message = "Text must not be empty")
    private String text;


    public long getIssueId() {
        return issueId;
    }

    public long getUserId() {
        return userId;
    }

    public String getText() {
        return text;
    }
}
