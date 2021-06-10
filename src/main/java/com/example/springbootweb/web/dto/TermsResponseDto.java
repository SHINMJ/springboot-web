package com.example.springbootweb.web.dto;

import com.example.springbootweb.domain.terms.Terms;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class TermsResponseDto {
    private Long id;
    private String type;
    private String title;
    private Boolean isUse;
    private LocalDateTime registDate;
    private ContentsResponseDto contents;

    public TermsResponseDto (Terms terms) {
        this.id = terms.getId();
        this.type = terms.getType();
        this.title = terms.getTitle();
        this.isUse = terms.getIsUse();
        this.registDate = terms.getRegistDate();
        this.contents = new ContentsResponseDto(terms.getContents());
    }


}
