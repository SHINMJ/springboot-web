package org.egovframe.cloud.domain.terms;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.egovframe.cloud.domain.BaseTimeEntity;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

/**
 * 이용약관 & 개인정보처리방침 entity
 */

@Getter
@NoArgsConstructor
@ToString(exclude = "details")
@Entity
public class Terms extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "terms_id")
    private Long id;
    /**
     * @TODO 공통코드 조회
     * 현재 string
     * terms of service (TOS) = 이용약관
     * privacy policy (PP) = 개인정보 처리 방침
     */
    @Column(nullable = false)
    private String type;

    @Column
    private String title;

    @Column(columnDefinition = "boolean default true")
    private Boolean isUse;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registDate;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "details_id")
    private Details details;

    @Builder
    public Terms(String type, String title, boolean isUse, LocalDateTime registDate, Details details) {
        this.type = type;
        this.title = title;
        this.isUse = isUse;
        this.registDate = registDate;
        this.details = details;
    }

    /**
     * 내용 수정
     * @param title 제목
     * @param isUse 사용여부
     * @param details 상세 내용
     * @return this
     */
    public Terms update(String title, boolean isUse, Details details) {
        this.title = title;
        this.isUse = isUse;
        this.details = details;
        return this;
    }

    /**
     * 사용여부 변경
     * @param isUse 사용여부
     * @return this
     */
    public Terms updateIsUSe(boolean isUse) {
        this.isUse = isUse;
        return this;
    }

}
