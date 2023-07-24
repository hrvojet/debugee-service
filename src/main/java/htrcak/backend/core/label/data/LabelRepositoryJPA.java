package htrcak.backend.core.label.data;

import htrcak.backend.core.label.Label;
import htrcak.backend.core.projects.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabelRepositoryJPA extends JpaRepository<Label, Long> {

    List<Label> findAllByProject(Project project);

}
