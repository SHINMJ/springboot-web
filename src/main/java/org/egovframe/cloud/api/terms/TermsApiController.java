package org.egovframe.cloud.api.terms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.cloud.api.terms.dto.TermsRequestDto;
import org.egovframe.cloud.api.terms.dto.TermsResponseDto;
import org.egovframe.cloud.api.terms.dto.TermsSaveRequestDto;
import org.egovframe.cloud.api.terms.dto.TermsUpdateRequestDto;
import org.egovframe.cloud.service.TermsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TermsApiController {

    private final TermsService termsService;

    @GetMapping("/api/v1/terms")
    public List<TermsResponseDto> search(TermsRequestDto requestDto) {
        return termsService.search(requestDto);
    }

    @GetMapping("/api/v1/terms/{id}")
    public ResponseEntity<TermsResponseDto> findById(@PathVariable Long id) {
        TermsResponseDto responseDto = termsService.findById(id);

        if(responseDto == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping("/api/v1/terms")
    public Long save(@RequestBody TermsSaveRequestDto requestDto) {
        return termsService.save(requestDto);
    }

    @PutMapping("/api/v1/terms/{id}")
    public Long update(@PathVariable Long id, @RequestBody TermsUpdateRequestDto requestDto) {
        return termsService.update(id, requestDto);
    }

    @PutMapping("/api/v1/terms/{id}/{isUse}")
    public Long updateIsUse(@PathVariable Long id, @PathVariable boolean isUse) {
        return termsService.updateIsUse(id, isUse);
    }

    @DeleteMapping("/api/v1/terms/{id}")
    public void delete(@PathVariable Long id) {
        termsService.delete(id);
    }
}
