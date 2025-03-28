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
import skysrd.githubissuefinder.dto.IssueSearchRequest;
import skysrd.githubissuefinder.model.Issue;
import skysrd.githubissuefinder.model.QIssue;
import skysrd.githubissuefinder.repository.IssueRepositoryCustom;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class IssueRepositoryImpl implements IssueRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Issue> findByIssueSearchRequest(IssueSearchRequest request) {
        QIssue issue = QIssue.issue;

        // IssueSearchRequest에서 Pageable 객체 가져오기
        Pageable pageable = request.getPageable();

        // 동적 쿼리 조건 생성
        BooleanBuilder builder = new BooleanBuilder();

        // 제목 검색
        if (StringUtils.hasText(request.getTitle())) {
            builder.and(issue.title.containsIgnoreCase(request.getTitle()));
        }

        // 상태 검색
        if (StringUtils.hasText(request.getStatus())) {
            builder.and(issue.status.eq(request.getStatus()));
        }

        // 리포지토리 ID로 검색
        if (request.getRepositoryId() != null) {
            builder.and(issue.repository.id.eq(request.getRepositoryId()));
        }

        // 레이블 ID로 검색
        if (request.getLabelIds() != null && !request.getLabelIds().isEmpty()) {
            builder.and(issue.labels.any().id.in(request.getLabelIds()));
        }

        // 저장소 언어로 검색 (추가된 부분)
        if (request.getLanguage() != null && !request.getLanguage().isEmpty()) {
            builder.and(issue.repository.language.in(request.getLanguage()));
        }

        // 조건에 맞는 총 데이터 수 조회
        Long total = queryFactory
                .select(issue.count())
                .from(issue)
                .where(builder)
                .fetchOne();

        // 정렬 처리
        List<OrderSpecifier<?>> orders = getOrderSpecifiers(pageable, issue);

        // 조건에 맞는 데이터 조회
        List<Issue> results = queryFactory
                .selectFrom(issue)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orders.toArray(new OrderSpecifier[0]))
                .fetch();

        return new PageImpl<>(results, pageable, total != null ? total : 0);
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(Pageable pageable, QIssue issue) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();

        if (pageable.getSort().isEmpty()) {
            orders.add(new OrderSpecifier<>(Order.DESC, issue.id));
            return orders;
        }

        PathBuilder<Issue> entityPath = new PathBuilder<>(Issue.class, "issue");

        for (Sort.Order order : pageable.getSort()) {
            Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

            // 아래 라인 수정: 타입 파라미터를 명시적으로 지정
            orders.add(new OrderSpecifier(direction, entityPath.get(order.getProperty(), Object.class)));
        }

        return orders;
    }
}