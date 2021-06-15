package com.example.springbootweb.domain.terms;

import com.example.springbootweb.web.dto.TermsRequestDto;
import com.example.springbootweb.web.dto.TermsResponseDto;

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
