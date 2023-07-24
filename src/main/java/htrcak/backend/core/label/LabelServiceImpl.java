package htrcak.backend.core.label;

import htrcak.backend.core.issues.Issue;
import htrcak.backend.core.issues.data.IssueRepositoryJPA;
import htrcak.backend.core.label.data.LabelRepositoryJPA;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelServiceImpl implements LabelService {

    LabelRepositoryJPA labelRepositoryJPA;

    IssueRepositoryJPA issueRepositoryJPA;

    public LabelServiceImpl(LabelRepositoryJPA labelRepositoryJPA, IssueRepositoryJPA issueRepositoryJPA) {
        this.labelRepositoryJPA = labelRepositoryJPA;
        this.issueRepositoryJPA = issueRepositoryJPA;
    }

    @Override
    public void doStuff(Long projectID) {
        List<Label> l = labelRepositoryJPA.findAll();
        System.out.println(l.get(1).getName());
        Issue i = issueRepositoryJPA.getById(1L);
        i.getLabelsSet().forEach(e -> System.out.println(e.getName()));
        System.out.println("###############");
        i.addLabel(l.get(1));
        i.getLabelsSet().forEach(e -> System.out.println(e.getName()));
        issueRepositoryJPA.save(i);
    }

    @Override
    public void doOtherStuff() {
        Issue i = issueRepositoryJPA.getById(1L);
        i.getLabelsSet().forEach(e -> System.out.println(e.getName()));
        System.out.println(i.getTitle());
        if (i.getLabelsSet().size() > 1) {
            i.removeLabel(labelRepositoryJPA.getById(2L));
            issueRepositoryJPA.save(i); // TODO ne zaboraviti save!!
        }
    }
}
