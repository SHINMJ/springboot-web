package org.egovframe.cloud.api.terms.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TermsUpdateRequestDto {
    private String title;
    private boolean isUse;
    private DetailsRequestDto details;

    @Builder
    public TermsUpdateRequestDto(String title, boolean isUse, DetailsRequestDto details) {
        this.title = title;
        this.isUse = isUse;
        this.details = details;
    }
}
