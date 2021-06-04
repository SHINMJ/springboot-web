package com.example.springbootweb.web;

import com.example.springbootweb.service.PostsService;
import com.example.springbootweb.web.dto.PostsResponseDto;
import com.example.springbootweb.web.dto.PostsSaveReqeustDto;
import com.example.springbootweb.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveReqeustDto reqeustDto) {
        return postsService.save(reqeustDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto dto) {
        return postsService.update(id, dto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }
}
