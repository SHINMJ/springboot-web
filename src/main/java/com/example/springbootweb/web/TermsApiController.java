package com.example.springbootweb.web;

import com.example.springbootweb.service.TermsService;
import com.example.springbootweb.web.dto.TermsRequestDto;
import com.example.springbootweb.web.dto.TermsResponseDto;
import com.example.springbootweb.web.dto.TermsSaveRequestDto;
import com.example.springbootweb.web.dto.TermsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class TermsApiController {

    private final TermsService termsService;

    @PostMapping("/api/v1/terms")
    public Long save(@RequestBody TermsSaveRequestDto requestDto) {
        return termsService.save(requestDto);
    }

    @GetMapping("/api/v1/terms")
    public List<TermsResponseDto> findAll(@RequestParam(name = "searchType", required = false) String searchType, @RequestParam(name = "value", required = false) String value) {
        System.out.println("=========== reuqestDto : " + searchType+", " + value);

        Optional<TermsRequestDto> requestDto = Optional.ofNullable(TermsRequestDto.builder()
                .searchType(searchType)
                .value(value)
                .build());

        return termsService.findAllByIsUseTrue(requestDto);
    }

    @GetMapping("/api/v1/terms/{id}")
    public ResponseEntity<TermsResponseDto> findById(@PathVariable Long id) {
        TermsResponseDto responseDto = termsService.findById(id);

        if(responseDto == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/api/v1/terms/{id}")
    public Long update(@PathVariable Long id, @RequestBody TermsUpdateRequestDto requestDto){
        return termsService.update(id, requestDto);
    }

    @DeleteMapping("/api/v1/terms/{id}")
    public void delete(@PathVariable Long id) {
        termsService.delete(id);
    }


}
