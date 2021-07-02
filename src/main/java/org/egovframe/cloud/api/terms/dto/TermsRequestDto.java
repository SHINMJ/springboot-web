package org.egovframe.cloud.api.terms.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 이용약관 관리 조회 request dto
 */

@Data
@NoArgsConstructor
public class TermsRequestDto {
    private String searchType;
    private String value;

    @Builder
    public TermsRequestDto(String searchType, String value) {
        this.searchType = searchType;
        this.value = value;
    }
}
