package htrcak.backend.core.issues.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IssuePatchValidator {

    private String title;

    private String issueType;

    @JsonProperty
    private boolean isOpened;


    public String getTitle() {
        return title;
    }

    public String getIssueType() {
        return issueType;
    }

    public boolean isOpened() {
        return isOpened;
    }
}
