package skysrd.githubissuefinder.dto;

import lombok.Builder;
import lombok.Getter;
import skysrd.githubissuefinder.model.Issue;
import skysrd.githubissuefinder.model.Label;

import java.util.HashSet;
import java.util.Set;

@Getter
public class IssueResponse {
    private Long id;
    private String url;
    private String title;
    private String createdAt;
    private String status;
    private RepoResponse repository;
    private Set<Label> labels = new HashSet<>();

    @Builder
    public IssueResponse(Long id, String url, String title, String createdAt, String status, RepoResponse repository, Set<Label> labels) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.createdAt = createdAt;
        this.status = status;
        this.repository = repository;
        this.labels = labels;
    }

    public static IssueResponse from(Issue issue) {
        return IssueResponse.builder()
                .id(issue.getId())
                .url(issue.getUrl())
                .title(issue.getTitle())
                .createdAt(issue.getCreatedAt())
                .status(issue.getStatus())
                .repository(RepoResponse.from(issue.getRepository()))
                .labels(issue.getLabels())
                .build();
    }
}
