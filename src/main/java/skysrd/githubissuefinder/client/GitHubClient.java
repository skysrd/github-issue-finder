package skysrd.githubissuefinder.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import skysrd.githubissuefinder.dto.github.GitHubRepoDto;

import java.util.Map;

@FeignClient(name = "github-api", url = "https://api.github.com")
public interface GitHubClient {
    @GetMapping("/repos/{owner}/{repo}")
    GitHubRepoDto getRepository(@PathVariable("owner") String owner,
                                @PathVariable("repo") String repo);

    @GetMapping("/repos/{owner}/{repo}/languages")
    Map<String, Integer> getRepositoryLanguages(@PathVariable("owner") String owner,
                                                @PathVariable("repo") String repo);
}