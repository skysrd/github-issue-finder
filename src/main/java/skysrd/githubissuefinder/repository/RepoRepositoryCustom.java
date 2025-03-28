package skysrd.githubissuefinder.repository;

import org.springframework.data.domain.Page;
import skysrd.githubissuefinder.dto.RepoSearchRequest;
import skysrd.githubissuefinder.model.Repo;

public interface RepoRepositoryCustom {
    Page<Repo> findByRepoSearchRequest(RepoSearchRequest request);
}