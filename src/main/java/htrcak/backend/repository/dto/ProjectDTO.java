package htrcak.backend.repository.dto;

import htrcak.backend.repository.models.Issue;

import java.util.Set;

public class ProjectDTO {

    private long id;

    private String title;

    private String description;

    public ProjectDTO(long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

}
