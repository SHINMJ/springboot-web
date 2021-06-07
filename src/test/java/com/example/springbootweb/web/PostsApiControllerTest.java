package com.example.springbootweb.web;

import com.example.springbootweb.domain.posts.Posts;
import com.example.springbootweb.domain.posts.PostsRepository;
import com.example.springbootweb.web.dto.PostsResponseDto;
import com.example.springbootweb.web.dto.PostsSaveReqeustDto;
import com.example.springbootweb.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    private String getUrl(Long id) {
        return "http://localhost:" + port + "/api/v1/posts"+(id > -1L?"/"+id:"");
    }

    @Test
    public void Posts_등록된다() throws Exception {
        //given
        String title = "제목";
        String content = "contents!!!!";
        PostsSaveReqeustDto dto =  PostsSaveReqeustDto.builder()
                .title(title)
                .content(content)
                .author("violet")
                .build();

        String url = getUrl(-1L);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, dto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);

    }

    @Test
    public void Posts_수정된다() throws Exception {
        //given
        String title = "update title";
        String content = "contents";
        String author = "violet";
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());
        Long id = savedPosts.getId();
        String expectedContents = "update contents";
        String expectedAuthor = "shinmj";
        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .contents(expectedContents)
                .author(expectedAuthor).build();

        String url = getUrl(id);
        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList.get(0).getTitle()).isEqualTo(title);
        assertThat(postsList.get(0).getContent()).isEqualTo(expectedContents);
        assertThat(postsList.get(0).getAuthor()).isEqualTo(expectedAuthor);
    }

    @Test
    public void Posts_한건조회한다() {
        //given
        String title = "title";
        String content = "contents";
        String author = "violet";

        Posts savedPosts = postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build()
        );

        Long id = savedPosts.getId();

        String url = getUrl(id);

        //when
        ResponseEntity<PostsResponseDto> responseEntity = restTemplate.getForEntity(url, PostsResponseDto.class);
//        PostsResponseDto posts = restTemplate.getForObject(url, PostsResponseDto.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        PostsResponseDto posts = responseEntity.getBody();
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
        assertThat(posts.getAuthor()).isEqualTo(author);
    }

}