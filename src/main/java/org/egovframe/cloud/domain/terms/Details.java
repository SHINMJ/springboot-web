package org.egovframe.cloud.domain.terms;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.egovframe.cloud.domain.BaseTimeEntity;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

/**
 * 이용약관 & 개인정보처리방침 상세 내용 (@TODO 다른 메뉴에서도 사용할지 여부)
 * contents = 내용
 * url = 내용에 대한 url
 */
@Getter
@NoArgsConstructor
@ToString
@Entity
public class Details extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "details_id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String contents;

    @Column
    private String contentsUrl;

    /**
     * @TODO
     * 다른 메뉴에서도 가져간다면 연관관계는 부모쪽에서만 가져가도록 한다.(단방향)
     * 현재는 양방향
     */
//    @OneToOne(mappedBy = "details", fetch = LAZY)
//    private Terms terms;

    @Builder
    public Details (String contents, String contentsUrl){
        this.contents = contents;
        this.contentsUrl = contentsUrl;
    }

    /**
     * 내용 수정
     * @param contents
     * @return
     */
    public Details updateContents(String contents) {
        this.contents = contents;

        return this;
    }

    /**
     * url 수정
     * @param contentsUrl
     * @return
     */
    public Details updateContentsUrl(String contentsUrl) {
        this.contentsUrl = contentsUrl;

        return this;
    }

    /**
     * 내용 및 url 수정
     * @param contents
     * @param contentsUrl
     * @return
     */
    public Details update(String contents, String contentsUrl) {
        this.contents = contents;
        this.contentsUrl = contentsUrl;
        return this;
    }
}
