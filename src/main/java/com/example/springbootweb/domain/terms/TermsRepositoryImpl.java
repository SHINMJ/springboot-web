package com.example.springbootweb.domain.terms;

import com.example.springbootweb.web.dto.TermsRequestDto;
import com.example.springbootweb.web.dto.TermsResponseDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.springbootweb.domain.terms.QContents.contents1;
import static com.example.springbootweb.domain.terms.QTerms.terms;
import static com.querydsl.core.types.Projections.fields;
import static org.springframework.util.StringUtils.hasLength;

@Slf4j
public class TermsRepositoryImpl implements TermsRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public TermsRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<TermsResponseDto> search(TermsRequestDto requestDto) {
        log.info("requestDto = {}", requestDto);
        QContents qContents = new QContents("contents");
        return queryFactory
                .select(fields(TermsResponseDto.class,
                        terms.id,
                        terms.type,
                        terms.title,
                        terms.isUse,
                        terms.registDate
                ))
                .from(terms)
                .innerJoin(terms.contents, qContents)
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

        QContents qContents = new QContents("contents");
        if ("title".equals(searchType)) {
            return terms.title.containsIgnoreCase(value);
        } else if ("contents".equals(searchType)) {
            return qContents.contents.containsIgnoreCase(value);
        }
        return null;
    }
}
