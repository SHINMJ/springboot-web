package com.example.springbootweb.web.dto;

import com.example.springbootweb.domain.terms.Contents;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ContentsRequestDto {
    private Long id;
    private String contents;
    private String url;

    @Builder
    public ContentsRequestDto (String contents, String url) {
        this.contents = contents;
        this.url = url;
    }

    public ContentsRequestDto (Contents contents) {
        this.id = contents.getId();
        this.contents = contents.getContents();
        this.url = contents.getContentsUrl();
    }

    public void setContents(String updateContents) {
        this.contents = updateContents;
    }

    public Contents toEntity() {
        return Contents.builder()
                .contents(this.contents)
                .url(this.url)
                .build();
    }
}
