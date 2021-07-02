package org.egovframe.cloud.api.terms.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.egovframe.cloud.domain.terms.Details;

/**
 * 이용약관 상세 내용 응답 dto
 */
@Getter
@NoArgsConstructor
public class DetailsResponseDto {
    private Long id;
    private String contents;
    private String contentsUrl;

    @Builder
    public DetailsResponseDto(Details details) {
        this.id = details.getId();
        this.contents = details.getContents();
        this.contentsUrl = details.getContentsUrl();
    }
}
