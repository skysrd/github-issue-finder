package skysrd.githubissuefinder.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Issue {
    @Id
    private Long id;

    private String url;
    private String title;
    private String createdAt;
    private String status;

    @ManyToOne
    @JoinColumn(name = "repository_id")
    private Repo repository;

    @ManyToMany
    @JoinTable(
            name = "issue_label",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    private Set<Label> labels = new HashSet<>();

    // 레이블 추가 메서드
    public void addLabel(Label label) {
        this.labels.add(label);
        label.getIssues().add(this);
    }

    // 레이블 제거 메서드
    public void removeLabel(Label label) {
        this.labels.remove(label);
        label.getIssues().remove(this);
    }
}