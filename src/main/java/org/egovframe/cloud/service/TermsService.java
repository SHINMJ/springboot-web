package org.egovframe.cloud.service;

import lombok.RequiredArgsConstructor;
import org.egovframe.cloud.api.terms.dto.TermsRequestDto;
import org.egovframe.cloud.api.terms.dto.TermsResponseDto;
import org.egovframe.cloud.api.terms.dto.TermsSaveRequestDto;
import org.egovframe.cloud.api.terms.dto.TermsUpdateRequestDto;
import org.egovframe.cloud.domain.terms.Terms;
import org.egovframe.cloud.domain.terms.TermsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TermsService {
    private final TermsRepository termsRepository;

    public List<TermsResponseDto> search(TermsRequestDto requestDto) {
        return termsRepository.search(requestDto);
    }

    public TermsResponseDto findById(Long id){
        Optional<Terms> optionalTerms = termsRepository.findById(id);
        if(optionalTerms.isPresent()){
                return new TermsResponseDto(optionalTerms.get());
        }

        return null;
    }

    @Transactional
    public Long save(TermsSaveRequestDto requestDto){
        Terms terms = termsRepository.save(requestDto.toEntity());

        return terms.getId();
    }

    @Transactional
    public Long update(Long id, TermsUpdateRequestDto requestDto) {
        Terms terms = termsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 약관이 없습니다. ID="+id));
        terms.update(requestDto.getTitle(), requestDto.isUse(), requestDto.getDetails().toEntity());
        return id;
    }

    @Transactional
    public void delete(Long id) {
        Terms terms = termsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 약관이 없습니다. ID="+id));
        termsRepository.delete(terms);
    }

    @Transactional
    public Long updateIsUse(Long id, boolean isUse) {
        Terms terms = termsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 약관이 없습니다. ID="+id));
        terms.updateIsUSe(isUse);

        return id;
    }

}
