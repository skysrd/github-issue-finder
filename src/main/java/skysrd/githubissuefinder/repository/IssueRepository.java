package skysrd.githubissuefinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skysrd.githubissuefinder.model.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long> {
}
