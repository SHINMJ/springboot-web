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
public class TermsSaveRequestDto {
    private String type;
    private String title;
    private Boolean isUse;
    private LocalDateTime registDate;
    private ContentsRequestDto contents;

    @Builder
    public TermsSaveRequestDto(String type, String title, Boolean isUse, LocalDateTime registDate, ContentsRequestDto contents) {
        this.type = type;
        this.title = title;
        this.isUse = isUse;
        this.registDate = registDate;
        this.contents = contents;
    }

    public Terms toEntity() {
        return Terms.builder()
                .type(this.type)
                .title(this.title)
                .isUse(this.isUse)
                .registDate(this.registDate)
                .contents(this.contents.toEntity())
                .build();
    }
}
