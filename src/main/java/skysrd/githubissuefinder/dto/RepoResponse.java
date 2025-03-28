package skysrd.githubissuefinder.dto;

import lombok.Builder;
import lombok.Getter;
import skysrd.githubissuefinder.model.Repo;

import java.time.LocalDateTime;

@Getter
public class RepoResponse {
    private String name;
    private String language;
    private String url;
    private Integer stars;
    private Integer forks;
    private LocalDateTime lastUpdatedAt;
    private Integer openIssues;

    @Builder(builderClassName = "builder", builderMethodName = "builder")
    public RepoResponse(String name, String language, String url, Integer stars, Integer forks, LocalDateTime lastUpdatedAt, Integer openIssues) {
        this.name = name;
        this.language = language;
        this.url = url;
        this.stars = stars;
        this.forks = forks;
        this.lastUpdatedAt = lastUpdatedAt;
        this.openIssues = openIssues;
    }

    public static RepoResponse from(Repo repo) {
        return RepoResponse.builder()
                .name(repo.getName())
                .language(repo.getLanguage())
                .url(repo.getUrl())
                .stars(repo.getStars())
                .forks(repo.getForks())
                .lastUpdatedAt(repo.getLastUpdatedAt())
                .openIssues(repo.getOpenIssues())
                .build();
    }
}