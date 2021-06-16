package com.example.springbootweb.web.dto;

import com.example.springbootweb.domain.terms.Contents;
import com.example.springbootweb.domain.terms.Terms;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class TermsUpdateRequestDto {
    private String title;
    private Boolean isUse;
    private ContentsRequestDto contents;

    @Builder
    public TermsUpdateRequestDto(String title, Boolean isUse, ContentsRequestDto contents) {
        this.title = title;
        this.isUse = isUse;
        this.contents = contents;
    }

    @Builder
    public TermsUpdateRequestDto(Boolean isUse){
        this.isUse = isUse;
    }

}
