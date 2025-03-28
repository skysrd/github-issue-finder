package skysrd.githubissuefinder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import skysrd.githubissuefinder.dto.RepoResponse;
import skysrd.githubissuefinder.dto.RepoSearchRequest;
import skysrd.githubissuefinder.service.RepoService;

@RestController
@RequestMapping("/api/repos")
@RequiredArgsConstructor
public class RepoController {
    private final RepoService repoService;

    @GetMapping("/")
    public ResponseEntity<Page<RepoResponse>> getRepos(RepoSearchRequest repoSearchRequest) {
        Page<RepoResponse> repos = repoService.getRepos(repoSearchRequest);
        return ResponseEntity.ok(repos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepoResponse> getRepo(@PathVariable Long id) {
        RepoResponse detailRepoResponse = repoService.getRepo(id);
        return ResponseEntity.ok(detailRepoResponse);
    }

    @PostMapping("/")
    public ResponseEntity<RepoResponse> addRepo(String url) {
        RepoResponse repoResponse = repoService.addRepo(url);
        return ResponseEntity.ok(repoResponse);
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> deleteRepo(Long id) {
        repoService.deleteRepo(id);
        return ResponseEntity.noContent().build();
    }
}
