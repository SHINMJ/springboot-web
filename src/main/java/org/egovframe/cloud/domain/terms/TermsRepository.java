package org.egovframe.cloud.domain.terms;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TermsRepository extends JpaRepository<Terms, Long>, TermsRepositoryCustom {
}
