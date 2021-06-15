package com.example.springbootweb.domain.terms;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TermsRepository extends JpaRepository<Terms, Long>, TermsRepositoryCustom {

    List<Terms> findAllByTypeOrderByCreatedAtDesc(String type);
    List<Terms> findAllByIsUseTrueOrderByRegistDateDesc();

}
