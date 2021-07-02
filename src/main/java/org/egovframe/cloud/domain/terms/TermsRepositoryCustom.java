package org.egovframe.cloud.domain.terms;

import org.egovframe.cloud.api.terms.dto.TermsRequestDto;
import org.egovframe.cloud.api.terms.dto.TermsResponseDto;

import java.util.List;

/**
 * Querydsl 작성하기 위한 인터페이스
 *
 * @author jaeyeolkim
 * @since 2021.06.15
 */
public interface TermsRepositoryCustom {
    List<TermsResponseDto> search(TermsRequestDto requestDto);
}
