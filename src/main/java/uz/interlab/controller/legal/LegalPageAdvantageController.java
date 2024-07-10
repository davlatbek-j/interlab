package uz.interlab.controller.legal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.interlab.entity.legal.LegalPageAdvantage;
import uz.interlab.entity.legal.LegalPageBody;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.legal.LegalPageAdvantageDTO;
import uz.interlab.payload.legal.LegalPageBodyDTO;
import uz.interlab.service.legal.LegalPageAdvantageService;

import java.util.List;

@RestController
@RequestMapping("/legal-page-advantage")
@RequiredArgsConstructor
public class LegalPageAdvantageController {

    private final LegalPageAdvantageService legalPageAdvantageService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<LegalPageAdvantage>> createLegalPageAdvantage(
            @RequestBody LegalPageAdvantage legalPageAdvantage
    ) {
        return legalPageAdvantageService.create(legalPageAdvantage);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<LegalPageAdvantageDTO>> getById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return legalPageAdvantageService.findById(id, lang);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<LegalPageAdvantageDTO>>> getAll(
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return legalPageAdvantageService.findAll(lang);
    }

    @GetMapping("/get-full-data/{id}")
    public ResponseEntity<ApiResponse<LegalPageAdvantage>> getFullData(@PathVariable Long id) {
        return legalPageAdvantageService.findById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<LegalPageAdvantage>> updateLegalPageAdvantage(
            @PathVariable Long id,
            @RequestBody LegalPageAdvantage legalPageAdvantage
    ) {
        return legalPageAdvantageService.update(id, legalPageAdvantage);
    }

    @PutMapping("/change-active/{id}")
    public ResponseEntity<ApiResponse<?>> changeActive(@PathVariable Long id) {
        return legalPageAdvantageService.changeActive(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteLegalPageAdvantage(@PathVariable Long id) {
        return legalPageAdvantageService.deleteById(id);
    }

}
