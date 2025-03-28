package skysrd.githubissuefinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skysrd.githubissuefinder.model.Repo;

public interface RepoRepository extends JpaRepository<Repo, Long> {
    // Custom query methods can be defined here
    // For example, to find a repo by its name:
    // Repo findByName(String name);
}
