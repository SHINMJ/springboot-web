package org.egovframe.cloud.api.terms.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.egovframe.cloud.domain.terms.Terms;

import java.time.LocalDateTime;

/**
 * 이용약관 응답 dto
 */
@Data
@NoArgsConstructor
@ToString(exclude = "details")
public class TermsResponseDto {
    private Long id;
    private String type;
    private String title;
    private Boolean isUse;
    private LocalDateTime registDate;
    private DetailsResponseDto details;

    public TermsResponseDto(Terms terms) {
        this.id = terms.getId();
        this.type = terms.getType();
        this.title = terms.getTitle();
        this.isUse = terms.getIsUse();
        this.registDate = this.getRegistDate();
        this.details = DetailsResponseDto.builder().details(terms.getDetails()).build();
    }
}
