package com.example.springbootweb.domain.terms;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TermsRepositoryTest {

    @Autowired
    TermsRepository termsRepository;

    /**
     * 단위 테스트가 끝날때마다 수행되는 메소드
     * 테스트 데이터간 침범을 막기 위해 사용
     */
    @AfterEach
    public void cleanUp() {
        termsRepository.deleteAll();
    }

    @Test
    @Transactional
    public void terms_목록조회() {
        //given
        for (int i = 0; i < 2; i++) {
            String title = "title_"+i;
            String contentStr = "contents " + i;
            String type = "TOS";
            if(i > 0){
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

        //when
        List<Terms> termsList = termsRepository.findAll();
        termsList.stream().forEach(System.out::println);

        //then
        assertThat(termsList.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    public void terms_특정type조회() {
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

        //when
        List<Terms> terms = termsRepository.findAllByTypeOrderByCreatedAtDesc("PP");

        //then
        terms.forEach(t -> System.out.println(t.toString()));

    }

}