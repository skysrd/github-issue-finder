package skysrd.githubissuefinder.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skysrd.githubissuefinder.client.GitHubClient;
import skysrd.githubissuefinder.dto.RepoResponse;
import skysrd.githubissuefinder.dto.RepoSearchRequest;
import skysrd.githubissuefinder.dto.github.GitHubRepoDto;
import skysrd.githubissuefinder.model.Repo;
import skysrd.githubissuefinder.repository.RepoRepository;
import skysrd.githubissuefinder.service.RepoService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class RepoServiceImpl implements RepoService {
    private final RepoRepository repoRepository;
    private final GitHubClient gitHubClient;

    private static final Pattern GITHUB_URL_PATTERN =
            Pattern.compile("https?://github.com/([^/]+)/([^/]+)");

    @Override
    public Page<RepoResponse> getRepos(RepoSearchRequest request) {
        return null;
    }

    @Override
    public RepoResponse getRepo(Long id) {
        return repoRepository.findById(id)
                .map(RepoResponse::from)
                .orElseThrow(() -> new EntityNotFoundException("Repo not found with id: " + id));
    }

    @Override
    @Transactional
    public RepoResponse addRepo(String url) {
        // GitHub URL에서 정보 추출
        String[] ownerAndRepo = extractOwnerAndRepo(url);
        String owner = ownerAndRepo[0];
        String repoName = ownerAndRepo[1];

        // GitHub API 호출하여 리포지토리 정보 가져오기
        GitHubRepoDto gitHubRepo = gitHubClient.getRepository(owner, repoName);

        // 리포지토리 언어 정보 가져오기
        Map<String, Integer> languages = gitHubClient.getRepositoryLanguages(owner, repoName);
        String primaryLanguage = !languages.isEmpty() ?
                languages.entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .orElse(null)
                : null;

        // 날짜 변환
        LocalDateTime lastUpdated = null;
        if (gitHubRepo.getUpdatedAt() != null) {
            lastUpdated = LocalDateTime.parse(
                    gitHubRepo.getUpdatedAt().replace("Z", ""),
                    DateTimeFormatter.ISO_DATE_TIME);
        }

        // Repo 엔티티 생성 및 저장
        Repo repo = Repo.fromDto(gitHubRepo, primaryLanguage, lastUpdated);
        Repo savedRepo = repoRepository.save(repo);
        return RepoResponse.from(savedRepo);
    }

    private String[] extractOwnerAndRepo(String url) {
        Matcher matcher = GITHUB_URL_PATTERN.matcher(url);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("유효하지 않은 GitHub URL 형식입니다: " + url);
        }

        return new String[] {matcher.group(1), matcher.group(2)};
    }

    @Override
    public void deleteRepo(Long id) {
        repoRepository.deleteById(id);
    }
}