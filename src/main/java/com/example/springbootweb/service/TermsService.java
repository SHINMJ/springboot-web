package com.example.springbootweb.service;

import com.example.springbootweb.domain.terms.Terms;
import com.example.springbootweb.domain.terms.TermsRepository;
import com.example.springbootweb.web.dto.TermsRequestDto;
import com.example.springbootweb.web.dto.TermsResponseDto;
import com.example.springbootweb.web.dto.TermsSaveRequestDto;
import com.example.springbootweb.web.dto.TermsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TermsService {

    private final TermsRepository termsRepository;

    @Transactional(readOnly = true)
    public List<TermsResponseDto> findAllByIsUseTrue(Optional<TermsRequestDto> requestDto) {
        System.out.println("=== service "+ requestDto.isPresent());
        if(requestDto.isPresent()){
            System.out.println(requestDto.get().toString());
        }
        List<Terms> list = termsRepository.findAllByIsUseTrueOrderByRegistDateDesc();
//        list.stream().forEach(terms -> System.out.println("in service : " + terms.toString()));
        return list.stream().map(terms -> new TermsResponseDto(terms)).collect(Collectors.toList());

    }

    public TermsResponseDto findById(Long id) {
        Optional<Terms> terms = termsRepository.findById(id);

        if(terms.isPresent()) {
            return new TermsResponseDto(terms.get());
        }

        return null;
    }

    @Transactional
    public Long save(TermsSaveRequestDto requestDto) {
        Terms terms = requestDto.toEntity();
        termsRepository.save(terms);
        return terms.getId();
    }

    @Transactional
    public Long update(Long id, TermsUpdateRequestDto requestDto) {
        Terms terms = termsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 약관이 없습니다. id="+id));
        terms.update(requestDto.getTitle(), requestDto.getIsUse(), requestDto.getContents().toEntity());

        return id;
    }

    @Transactional
    public void delete(Long id) {
        Terms terms = termsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 약관이 없습니다. id="+id));
        termsRepository.delete(terms);
    }
}
