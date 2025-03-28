package skysrd.githubissuefinder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import skysrd.githubissuefinder.model.Repo;
import skysrd.githubissuefinder.service.RepoService;

@RestController
@RequestMapping("/api/repos")
@RequiredArgsConstructor
public class RepoController {
    private final RepoService repoService;

    @GetMapping("/")
    public ResponseEntity<Page<Repo>> getRepos(SearchRequest searchRequest) {
        Page<Repo> repos = repoService.getRepos(searchRequest);
        return ResponseEntity.ok(repos);
    }

    @GetMapping("/")
    public ResponseEntity<DetailRepoResponse> getRepo(@RequestParam Long id) {
        DetailRepoResponse detailRepoResponse = repoService.getRepo(id);
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
