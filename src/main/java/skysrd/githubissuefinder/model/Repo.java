package skysrd.githubissuefinder.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import skysrd.githubissuefinder.dto.github.GitHubRepoDto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Repo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String language;
    private String url;
    private Integer stars;
    private Integer forks;
    private Integer openIssues;
    private LocalDateTime lastUpdatedAt;

    @OneToMany(mappedBy = "repository")
    private Set<Issue> issues = new HashSet<>();

    @Builder
    public Repo(Long id, String name, String language, String url, Integer stars, Integer forks, Integer openIssues, LocalDateTime lastUpdatedAt, Set<Issue> issues) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.url = url;
        this.stars = stars;
        this.forks = forks;
        this.openIssues = openIssues;
        this.lastUpdatedAt = lastUpdatedAt;
        this.issues = issues;
    }

    public static Repo fromDto(GitHubRepoDto repoDto, String primaryLanguage, LocalDateTime lastUpdatedAt) {
        return Repo.builder()
                .name(repoDto.getFullName())
                .language(primaryLanguage)
                .url(repoDto.getHtmlUrl())
                .stars(repoDto.getStargazersCount())
                .forks(repoDto.getForksCount())
                .openIssues(repoDto.getOpenIssuesCount())
                .lastUpdatedAt(lastUpdatedAt)
                .build();
    }
}