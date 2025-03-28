package skysrd.githubissuefinder.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import skysrd.githubissuefinder.dto.IssueSearchRequest;
import skysrd.githubissuefinder.model.Issue;

public interface IssueRepositoryCustom {
    Page<Issue> findByIssueSearchRequest(IssueSearchRequest issueSearchRequest);

}
