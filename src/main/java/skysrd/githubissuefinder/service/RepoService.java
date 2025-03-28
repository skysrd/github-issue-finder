package skysrd.githubissuefinder.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import skysrd.githubissuefinder.dto.RepoResponse;
import skysrd.githubissuefinder.dto.RepoSearchRequest;
import skysrd.githubissuefinder.model.Repo;

public interface RepoService {
    Page<RepoResponse> getRepos(RepoSearchRequest repoSearchRequest);
    RepoResponse getRepo(Long id);
    RepoResponse addRepo(String url);
    void deleteRepo(Long id);

}
