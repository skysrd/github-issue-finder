package skysrd.githubissuefinder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skysrd.githubissuefinder.dto.IssueSearchRequest;
import skysrd.githubissuefinder.model.Issue;
import skysrd.githubissuefinder.service.IssueService;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class IssueController {
    private final IssueService issueService;

    @GetMapping("/")
    public ResponseEntity<Page<Issue>> getIssues(@RequestBody IssueSearchRequest issueSearchRequest) {
        Page<Issue> issues = issueService.getIssues(issueSearchRequest);
        return ResponseEntity.ok(issues);
    }
}
