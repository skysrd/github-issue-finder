package skysrd.githubissuefinder.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import skysrd.githubissuefinder.dto.IssueSearchRequest;
import skysrd.githubissuefinder.model.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long>, QuerydslPredicateExecutor<Issue>, IssueRepositoryCustom{
}
