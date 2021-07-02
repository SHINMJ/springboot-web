package org.egovframe.cloud.domain.terms;

import org.egovframe.cloud.api.terms.dto.TermsRequestDto;
import org.egovframe.cloud.api.terms.dto.TermsResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TermsRepositoryTest {

    @Autowired
    EntityManager em;

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
            Details contents = Details.builder()
                    .contents(contentStr)
                    .contentsUrl("").build();

            termsRepository.save(Terms.builder()
                    .type(type)
                    .title(title)
                    .details(contents)
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
    public void terms_조건부조회() {
        //given
        for (int i = 1; i <= 10; i++) {
            String title = "title_"+i;
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

        TermsRequestDto requestDto = TermsRequestDto.builder()
                .searchType("title")
                .value("1")
                .build();

        //when
        List<TermsResponseDto> terms = termsRepository.search(requestDto);

        //then
        terms.forEach(t -> System.out.println(t.toString()));

    }

    @Test
    @Transactional
    public void terms_한건조회() throws Exception {
        //given
        for (int i = 1; i <= 10; i++) {
            String title = "title_"+i;
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

        //when
        Optional<Terms> optionalTerms = termsRepository.findById(1L);

        //then
        System.out.println(optionalTerms.get());
        System.out.println(optionalTerms.get().getDetails().toString());
        assertThat(optionalTerms.get().getTitle()).isEqualTo("title_1");
        assertThat(optionalTerms.get().getDetails().getContents()).isEqualTo("contents 1");

    }

    @Test
    @Transactional
    public void terms_update() {
        String updateTitle = "update title";
        String updateContents = "update Contents";
        //given
        for (int i = 1; i <= 10; i++) {
            String title = "title_"+i;
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

        //when
        Optional<Terms> optionalTerms = termsRepository.findById(1L);
        Terms terms = optionalTerms.get();
        Details details = terms.getDetails();

        terms.update(updateTitle, true, details.updateContents(updateContents));
        em.persist(terms);

        //then
        Optional<Terms> updatedTerms = termsRepository.findById(1L);

        assertThat(updatedTerms.get().getTitle()).isEqualTo(updateTitle);
        Details updatedDetails = updatedTerms.get().getDetails();
        assertThat(updatedDetails.getContents()).isEqualTo(updateContents);

        System.out.println(updatedTerms.get());
        System.out.println(updatedDetails);

    }

}