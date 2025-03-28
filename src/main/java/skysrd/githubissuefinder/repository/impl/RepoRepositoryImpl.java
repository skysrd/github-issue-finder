package skysrd.githubissuefinder.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import skysrd.githubissuefinder.dto.RepoSearchRequest;
import skysrd.githubissuefinder.model.Repo;
import skysrd.githubissuefinder.model.QRepo;
import skysrd.githubissuefinder.repository.RepoRepositoryCustom;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class RepoRepositoryImpl implements RepoRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Repo> findByRepoSearchRequest(RepoSearchRequest request) {
        QRepo repo = QRepo.repo;

        // RepoSearchRequest에서 Pageable 객체 가져오기
        Pageable pageable = request.getPageable();

        // 동적 쿼리 조건 생성
        BooleanBuilder builder = new BooleanBuilder();

        // 이름 검색
        if (StringUtils.hasText(request.getName())) {
            builder.and(repo.name.containsIgnoreCase(request.getName()));
        }

        // 언어 검색
        if (request.getLanguage() != null && !request.getLanguage().isEmpty()) {
            builder.and(repo.language.in(request.getLanguage()));
        }

        // 최소 스타 수
        if (request.getMinStars() != null) {
            builder.and(repo.stars.goe(request.getMinStars()));
        }

        // 최소 포크 수
        if (request.getMinForks() != null) {
            builder.and(repo.forks.goe(request.getMinForks()));
        }

        // 조건에 맞는 총 데이터 수 조회
        Long total = queryFactory
                .select(repo.count())
                .from(repo)
                .where(builder)
                .fetchOne();

        // 정렬 처리
        List<OrderSpecifier<?>> orders = getOrderSpecifiers(pageable, repo);

        // 조건에 맞는 데이터 조회
        List<Repo> results = queryFactory
                .selectFrom(repo)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orders.toArray(new OrderSpecifier[0]))
                .fetch();

        return new PageImpl<>(results, pageable, total != null ? total : 0);
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(Pageable pageable, QRepo repo) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();

        if (pageable.getSort().isEmpty()) {
            orders.add(new OrderSpecifier<>(Order.DESC, repo.id));
            return orders;
        }

        PathBuilder<Repo> entityPath = new PathBuilder<>(Repo.class, "repo");

        for (Sort.Order order : pageable.getSort()) {
            Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
            orders.add(new OrderSpecifier(direction, entityPath.get(order.getProperty(), Object.class)));
        }

        return orders;
    }
}