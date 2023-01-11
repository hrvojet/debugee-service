package htrcak.backend.issues.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotBlank;

public class IssuePatchValidator {

    private String title;

    private String issueType;


    public String getTitle() {
        return title;
    }

    public String getIssueType() {
        return issueType;
    }
}
