package com.example.springbootweb.service;

import com.example.springbootweb.domain.posts.Posts;
import com.example.springbootweb.domain.posts.PostsRepository;
import com.example.springbootweb.web.dto.PostsResponseDto;
import com.example.springbootweb.web.dto.PostsSaveReqeustDto;
import com.example.springbootweb.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveReqeustDto reqeustDto){
        return postsRepository.save(reqeustDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("posts가 없어요. id = " + id));

        posts.update(requestDto.getContents(), requestDto.getAuthor());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("posts가 없어요. id = " + id));

        return new PostsResponseDto(entity);
    }

}
