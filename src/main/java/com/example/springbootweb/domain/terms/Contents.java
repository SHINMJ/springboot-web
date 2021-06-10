package com.example.springbootweb.domain.terms;

import com.example.springbootweb.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Getter
@NoArgsConstructor
@Entity
public class Contents extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contents_id")
    private Long id;

    @Column
    private String contents;

    @Column
    private String url;

    @Builder
    public Contents(String contents, String url) {
        this.contents = contents;
        this.url = url;
    }

    public Contents updateContents(String contents) {
        this.contents = contents;

        return this;
    }

    public Contents updateUrl(String url) {
        this.url = url;

        return this;
    }
}
