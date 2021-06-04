package com.example.springbootweb.web.dto;

import com.example.springbootweb.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {

    private String contents;
    private String author;

    @Builder
    public PostsUpdateRequestDto(String contents, String author){
        this.contents = contents;
        this.author = author;
    }

    public Posts toEntity() {
        return Posts.builder()
                .content(contents)
                .author(author)
                .build();
    }


}
