package skysrd.githubissuefinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import skysrd.githubissuefinder.model.Repo;

public interface RepoRepository extends JpaRepository<Repo, Long>, QuerydslPredicateExecutor<Repo>, RepoRepositoryCustom {
    // Custom query methods can be defined here
    // For example, to find a repo by its name:
    // Repo findByName(String name);
}
