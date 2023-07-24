package htrcak.backend.core.label.data;

import htrcak.backend.core.label.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepositoryJPA extends JpaRepository<Label, Long> {

}
