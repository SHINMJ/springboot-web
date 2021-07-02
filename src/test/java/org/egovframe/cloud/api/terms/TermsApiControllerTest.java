package org.egovframe.cloud.api.terms;

import org.egovframe.cloud.api.terms.dto.*;
import org.egovframe.cloud.domain.terms.Details;
import org.egovframe.cloud.domain.terms.Terms;
import org.egovframe.cloud.domain.terms.TermsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TermsApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    private TermsRepository termsRepository;

//    @BeforeEach
//    public void setup() {
//        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
//    }

    @AfterEach
    public void teardown() {
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
    public void 등록_byWebflux() throws Exception {
        //given
        String type = "PP";
        String title= "test title";
        String contents = "test contents";
        DetailsRequestDto detailsRequestDto = DetailsRequestDto.builder()
                .contents(contents)
                .contentsUrl("")
                .build();
        TermsSaveRequestDto requestDto = TermsSaveRequestDto.builder()
                .type(type)
                .title(title)
                .isUse(true)
                .registDate(LocalDateTime.now())
                .details(detailsRequestDto)
                .build();
        String url = getUrl();
        //when, then
        ResponseSpec responseSpec = webTestClient.post()
                .uri(url)
                .body(Mono.just(requestDto), TermsRequestDto.class)
                .exchange()
                .expectStatus()
                .isOk();


    }

    @Test
    @Transactional
    public void 등록_byRestTemplate() throws Exception {
        //given
        String type = "PP";
        String title= "test title";
        String contents = "test contents";
        DetailsRequestDto detailsRequestDto = DetailsRequestDto.builder()
                .contents(contents)
                .contentsUrl("")
                .build();
        TermsSaveRequestDto requestDto = TermsSaveRequestDto.builder()
                .type(type)
                .title(title)
                .isUse(true)
                .registDate(LocalDateTime.now())
                .details(detailsRequestDto)
                .build();
        String url = getUrl();

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Terms terms = termsRepository.findById(responseEntity.getBody().longValue()).get();
        assertThat(terms.getType()).isEqualTo(type);
        assertThat(terms.getTitle()).isEqualTo(title);

    }

    @Test
    public void 목록조회() throws Exception {
        //given
        for (int i = 1; i <= 10; i++) {
            String title = "title_"+i;
            String contentStr = "contents " + i;
            String type = "TOS";
            if(i % 2 == 0){
                type = "PP";
            }
            Details details = Details.builder()
                    .contents(contentStr)
                    .contentsUrl("")
                    .build();

            termsRepository.save(Terms.builder()
                    .type(type)
                    .title(title)
                    .details(details)
                    .build());
        }

        String url = getUrl();

        //when
        ResponseEntity<List<TermsResponseDto>> responseEntity = restTemplate.exchange(url,HttpMethod.GET, null, new ParameterizedTypeReference<List<TermsResponseDto>>() {});

        //then
        List<TermsResponseDto> list = responseEntity.getBody();
        assertThat(list.size()).isEqualTo(10);
        list.stream().forEach(t -> System.out.println("in test : "+t.toString()));

    }

    @Test
    public void 검색조건_목록_조회() {
        //given
        for (int i = 1; i <= 10; i++) {
            String title = "title_"+i;
            if(i%3 == 0){
                title = "api test title";
            }
            String contentStr = "contents " + i;
            String type = "TOS";
            if(i % 2 == 0){
                type = "PP";
            }
            Details contents = Details.builder()
                    .contents(contentStr).contentsUrl("").build();

            termsRepository.save(Terms.builder()
                    .type(type)
                    .title(title)
                    .details(contents)
                    .build());
        }

        String url = getUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity entity = new HttpEntity( headers);

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("searchType", "title")
                .queryParam("value", "api")
                .build();

        //when
        ResponseEntity<List<TermsResponseDto>> responseEntity = restTemplate.exchange(uriComponents.toUriString(),HttpMethod.GET, entity, new ParameterizedTypeReference<List<TermsResponseDto>>() {});

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().size()).isEqualTo(3);
    }

    @Test
    public void 한건조회() throws Exception {
        //given
        for (int i = 1; i <= 10; i++) {
            String title = "title_"+i;
            String contentStr = "contents " + i;
            String type = "TOS";
            if(i % 2 == 0){
                type = "PP";
            }
            Details details = Details.builder()
                    .contents(contentStr)
                    .contentsUrl("")
                    .build();

            termsRepository.save(Terms.builder()
                    .type(type)
                    .title(title)
                    .details(details)
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
    public void 이용약관_수정() {
        //given
        Long id = termsRepository.save(Terms.builder()
                .type("PP")
                .title("title")
                .details(Details.builder().contents("contents").contentsUrl("").build())
                .build()
        ).getId();

        TermsUpdateRequestDto requestDto = TermsUpdateRequestDto.builder()
                .isUse(false)
                .title("update title")
                .details(DetailsRequestDto.builder().contents("update contents").build())
                .build();

        String url = getUrl(id);
        HttpEntity<TermsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(id);
        Terms resTerm = termsRepository.findById(id).get();
        System.out.println(resTerm);

    }

    @Test
    public void 이용약관_삭제() {
        //given
        Long id = termsRepository.save(Terms.builder()
                .type("PP")
                .title("title")
                .details(Details.builder().contents("contents").build())
                .build()
        ).getId();
        String url = getUrl(id);

        //when
        restTemplate.delete(url);

        //then
        Optional<Terms> terms = termsRepository.findById(id);
        assertThat(terms.isPresent()).isFalse();
    }

    @Test
    public void 사용여부_수정() throws Exception {
        //given
        Long id = termsRepository.save(Terms.builder()
                .type("PP")
                .title("title")
                .isUse(true)
                .details(Details.builder().contents("contents").contentsUrl("").build())
                .build()
        ).getId();
        String url = getUrl(id);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url+"/false", HttpMethod.PUT, null, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(id);
        Terms resTerm = termsRepository.findById(id).get();
        System.out.println(resTerm);
        assertThat(resTerm.getIsUse()).isFalse();
    }

}