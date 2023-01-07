package htrcak.backend.projects.data;

import javax.validation.constraints.NotBlank;

public class ProjectPost {

    @NotBlank(message = "Title must not be empty")
    private String title;

    @NotBlank(message = "Description must not be empty")
    private String description;


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

}
