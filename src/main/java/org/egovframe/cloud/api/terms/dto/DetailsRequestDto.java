package org.egovframe.cloud.api.terms.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egovframe.cloud.domain.terms.Details;

@Data
@NoArgsConstructor
public class DetailsRequestDto {
    private Long id;
    private String contents;
    private String contentsUrl;

    @Builder
    public DetailsRequestDto(String contents, String contentsUrl) {
        this.contents = contents;
        this.contentsUrl = contentsUrl;
    }

    public DetailsRequestDto(Details details) {
        this.id = details.getId();
        this.contents = details.getContents();
        this.contentsUrl = details.getContentsUrl();
    }

    public Details toEntity() {
        return Details.builder()
                .contents(this.contents)
                .contentsUrl(this.contentsUrl)
                .build();
    }

}
