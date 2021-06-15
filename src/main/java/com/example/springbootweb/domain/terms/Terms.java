package com.example.springbootweb.domain.terms;

import com.example.springbootweb.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Terms extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JoinColumn(name = "contents_id")
    private Contents contents;

    @Builder
    public Terms(String type, String title, Boolean isUse, LocalDateTime registDate, Contents contents) {
        this.type = type;
        this.title = title;
        this.isUse = isUse;
        this.registDate = registDate;
        this.contents = contents;
    }

    public Terms update(String title, Boolean isUse, Contents contents) {
        this.title = title;
        this.isUse = isUse;
        this.contents = contents;

        return this;
    }

    public Terms updateUse(Boolean isUse) {
        this.isUse = isUse;

        return this;
    }

    @Override
    public String toString() {
        return "Terms{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", isUse=" + isUse +
                ", registDate=" + registDate +
//                ", contents=" + contents.toString() +
                ", createAt=" + this.getCreatedAt() +
                ", updateAt=" + this.getUpdatedAt() +
                '}';
    }
}
