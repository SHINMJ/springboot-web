package com.example.springbootweb.web.dto;

import com.example.springbootweb.domain.terms.Contents;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class ContentsResponseDto {
    private Long id;
    private String contents;
    private String url;

    public ContentsResponseDto(Contents contents) {
        this.id = contents.getId();
        this.contents = contents.getContents();
        this.url = contents.getUrl();
    }
}
