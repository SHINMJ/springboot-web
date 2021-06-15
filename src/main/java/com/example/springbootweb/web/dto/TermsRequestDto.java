package com.example.springbootweb.web.dto;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class TermsRequestDto {
    private String searchType;
    private String value;

    @Builder
    public TermsRequestDto(String searchType, String value) {
        this.searchType = searchType;
        this.value = value;
    }
}
