package com.example.springbootweb.web;

import com.example.springbootweb.domain.terms.Contents;
import com.example.springbootweb.domain.terms.Terms;
import com.example.springbootweb.domain.terms.TermsRepository;
import com.example.springbootweb.web.dto.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TermsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TermsRepository termsRepository;

    @AfterEach
    public void tearDown() throws Exception {
        termsRepository.deleteAll();
    }

    public String getUrl() {
        return getUrl(-1L);
    }

    public String getUrl(Long id) {
        StringBuffer sb = new StringBuffer();
        sb.append("http://localhost:");
        sb.append(port);
        sb.append("/api/v1/terms");
        if(id > -1){
            sb.append("/");
            sb.append(id);
        }
        return sb.toString();
    }

    @Test
    @Transactional
    public void 이용약관_등록_byContents() {
        //given
        String type = "TOS";
        String title = "test";
        ContentsRequestDto contents = ContentsRequestDto.builder().contents("test contents").url("").build();
        TermsSaveRequestDto reqeustDto = TermsSaveRequestDto.builder()
                .type(type)
                .title(title)
                .contents(contents)
                .build();

        String url = getUrl();

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, reqeustDto, Long.class);

        //then
//        List<Terms> list = termsRepository.findAll();
        Terms terms = termsRepository.findById(responseEntity.getBody().longValue()).get();
        System.out.println(terms.toString());
        assertThat(terms.getType()).isEqualTo(type);
        assertThat(terms.getTitle()).isEqualTo(title);
    }

    public Long[] saveTerms(int cnt) {
        Long[] ids = new Long[cnt];
        for (int i = 0; i < cnt; i++) {
            String type = "TOS";
            if(i % 2 == 0){
                type = "PP";
            }
            String title = "이용약관 " + i;
            Contents contents = Contents.builder().contents("이용약관 contents : " + i).url("").build();

            Long id = termsRepository.save(Terms.builder()
                    .type(type)
                    .title(title)
                    .contents(contents)
                    .build()
            ).getId();
            ids[i] = id;
        }

        return ids;
    }

    @Test
    public void 목록_조회() {
        //given
        for (int i = 1; i <= 10; i++) {
            String title = "title_"+i;
            String contentStr = "contents " + i;
            String type = "TOS";
            if(i % 2 == 0){
                type = "PP";
            }
            Contents contents = Contents.builder()
                    .contents(contentStr).url("").build();

            termsRepository.save(Terms.builder()
                    .type(type)
                    .title(title)
                    .contents(contents)
                    .build());
        }

        String url = getUrl();

        //when
        ResponseEntity<List<TermsResponseDto>> responseEntity = restTemplate.exchange(url,HttpMethod.GET, null, new ParameterizedTypeReference<List<TermsResponseDto>>() {});

        //then
        List<TermsResponseDto> list = responseEntity.getBody();
        assertThat(list.size()).isGreaterThan(0);
        System.out.println(list.size());
        list.stream().forEach(t -> System.out.println("in test : "+t.toString()));

    }

    @Test
//    @Transactional
    public void 한건_조회() {
        //given
        //given
        for (int i = 1; i <= 10; i++) {
            String title = "title_"+i;
            String contentStr = "contents " + i;
            String type = "TOS";
            if(i % 2 == 0){
                type = "PP";
            }
            Contents contents = Contents.builder()
                    .contents(contentStr).url("").build();

            termsRepository.save(Terms.builder()
                    .type(type)
                    .title(title)
                    .contents(contents)
                    .build());
        }

        String url = getUrl(1L);

        //when
        ResponseEntity<TermsResponseDto> responseEntity = restTemplate.getForEntity(url, TermsResponseDto.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        TermsResponseDto terms = responseEntity.getBody();
        System.out.println(terms.toString());
    }


    @Test
    public void 한건_조회_404() {
        //given
        saveTerms(10);

        String url = getUrl(123L);

        //when
        ResponseEntity<TermsResponseDto> responseEntity = restTemplate.getForEntity(url, TermsResponseDto.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
//    @Transactional
    public void 이용약관_수정() {
        //given
        Long id = termsRepository.save(Terms.builder()
                .type("PP")
                .title("title")
                .contents(Contents.builder().contents("contents").url("").build())
                .build()
        ).getId();

        TermsUpdateRequestDto requestDto = TermsUpdateRequestDto.builder()
                .isUse(false)
                .title("update title")
                .contents(ContentsRequestDto.builder().contents("update contents").build())
                .build();

        String url = getUrl(id);
        HttpEntity<TermsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(id);
//        Terms resTerm = termsRepository.findById(id).get();
//        System.out.println(resTerm);

    }

    @Test
    public void 이용약관_삭제() {
        //given
        Long id = termsRepository.save(Terms.builder()
                .type("PP")
                .title("title")
                .contents(Contents.builder().contents("contents").build())
                .build()
        ).getId();
        String url = getUrl(id);

        //when
        restTemplate.delete(url);

        //then
        Optional<Terms> terms = termsRepository.findById(id);
        assertThat(terms.isPresent()).isFalse();
    }

}