package org.egovframe.cloud.domain.terms;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.cloud.api.terms.dto.TermsRequestDto;
import org.egovframe.cloud.api.terms.dto.TermsResponseDto;

import javax.persistence.EntityManager;
import java.util.List;

import static com.querydsl.core.types.Projections.fields;
import static org.egovframe.cloud.domain.terms.QTerms.terms;
import static org.springframework.util.StringUtils.hasLength;

@Slf4j
public class TermsRepositoryImpl implements TermsRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public TermsRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<TermsResponseDto> search(TermsRequestDto requestDto) {
        log.info("requestDto = {}", requestDto);
        QDetails qDetails = new QDetails("details");
        return queryFactory
                .select(fields(TermsResponseDto.class,
                        terms.id,
                        terms.type,
                        terms.title,
                        terms.isUse,
                        terms.registDate
                ))
                .from(terms)
                .innerJoin(terms.details, qDetails)
                .where(
                        searchTextLike(requestDto)
                )
                .fetch();
    }

    private BooleanExpression searchTextLike(TermsRequestDto requestDto) {
        final String searchType = requestDto.getSearchType();
        final String value = requestDto.getValue();
        if (!hasLength(searchType) || !hasLength(value)) {
            return null;
        }

        QDetails qDetails = new QDetails("details");
        if ("title".equals(searchType)) {
            return terms.title.containsIgnoreCase(value);
        } else if ("contents".equals(searchType)) {
            return qDetails.contents.containsIgnoreCase(value);
        }
        return null;
    }
}
