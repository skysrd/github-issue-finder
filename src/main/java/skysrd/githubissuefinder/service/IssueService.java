package skysrd.githubissuefinder.service;

import org.springframework.data.domain.Page;
import skysrd.githubissuefinder.model.Issue;

public interface IssueService {
    Page<Issue> getIssues(IssueSearchRequest issueSearchRequest);
}
