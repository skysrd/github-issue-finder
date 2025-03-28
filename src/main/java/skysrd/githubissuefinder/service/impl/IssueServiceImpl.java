package skysrd.githubissuefinder.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import skysrd.githubissuefinder.dto.IssueSearchRequest;
import skysrd.githubissuefinder.model.Issue;
import skysrd.githubissuefinder.repository.IssueRepository;
import skysrd.githubissuefinder.service.IssueService;

@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {
    private final IssueRepository issueRepository;

    @Override
    public Page<Issue> getIssues(IssueSearchRequest issueSearchRequest) {
        return issueRepository.findByIssueSearchRequest(issueSearchRequest);
    }
}
