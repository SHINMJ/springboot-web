package org.egovframe.cloud.api.terms.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egovframe.cloud.domain.terms.Details;
import org.egovframe.cloud.domain.terms.Terms;

import java.time.LocalDateTime;

/**
 * 이용약관 저장 request dto
 */
@Data
@NoArgsConstructor
public class TermsSaveRequestDto {
    private String type;
    private String title;
    private boolean isUse;
    private LocalDateTime registDate;
    private DetailsRequestDto details;

    @Builder
    public TermsSaveRequestDto(String type, String title, boolean isUse, LocalDateTime registDate, DetailsRequestDto details) {
        this.type = type;
        this.title = title;
        this.isUse = isUse;
        this.registDate = registDate;
        this.details = details;
    }

    public Terms toEntity() {
        return Terms.builder()
                .type(this.type)
                .title(this.title)
                .isUse(this.isUse)
                .registDate(this.registDate)
                .details(this.details.toEntity())
                .build();
    }
}
