package htrcak.backend.core.label;

import htrcak.backend.core.issues.Issue;
import htrcak.backend.core.label.data.LabelPostValidator;
import htrcak.backend.core.projects.Project;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "label_name")
    private String name;

    @Column
    private String description;

    @Column
    private String colorHex;

    @ManyToOne
    @JoinColumn(name="project_id", referencedColumnName = "id")
    private Project project;

    @ManyToMany(mappedBy = "labelsSet")
    private Set<Issue> usedIssues = new HashSet<>();

    public Label() {}

    public Label(String name, String colorHex, Project project) {
        this.name = name;
        this.colorHex = colorHex;
        this.project = project;
    }

    public Label(LabelPostValidator labelPostValidator, Project project) {
        this.name = labelPostValidator.getName();
        this.colorHex = labelPostValidator.getColorHex();
        this.description = labelPostValidator.getDescription();
        this.project = project;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Set<Issue> getUsedIssues() {
        return usedIssues;
    }

    public void setUsedIssues(Set<Issue> usedIssues) {
        this.usedIssues = usedIssues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if(!(o instanceof Label))
            return false;

        return id != null && id.equals(((Label) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
