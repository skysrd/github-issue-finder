package skysrd.githubissuefinder.dto;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Set;

@Data
public class RepoSearchRequest {
    private String name;
    private String description;
    private Set<String> language;
    private Integer minStars;
    private Integer minForks;
    private String createdAfter;
    private String createdBefore;

    // 페이징 관련 필드
    private int page = 0;
    private int size = 20;
    private String sortBy = "id";
    private String direction = "DESC";

    public Pageable getPageable() {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        return PageRequest.of(page, size, sort);
    }
}