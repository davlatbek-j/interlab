package uz.interlab.controller.legal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.legal.LegalPageHead;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.legal.LegalPageHeadDTO;
import uz.interlab.service.legal.LegalPageHeadService;

import java.util.List;

@RestController
@RequestMapping("/legal-page-head")
@RequiredArgsConstructor
public class LegalPageHeadController {

    private final LegalPageHeadService legalPageHeadService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<LegalPageHead>> createLegalPageHead(
            @RequestParam(value = "json") String legalPageHead,
            @RequestPart(value = "photo") MultipartFile photo
    ) {
        return legalPageHeadService.create(legalPageHead, photo);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<LegalPageHeadDTO>> getById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return legalPageHeadService.findById(id, lang);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<LegalPageHeadDTO>>> getAll(
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return legalPageHeadService.findAll(lang);
    }

    @GetMapping("/get-full-data/{id}")
    public ResponseEntity<ApiResponse<LegalPageHead>> getFullData(@PathVariable Long id) {
        return legalPageHeadService.findById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<LegalPageHead>> updateLegalPageHead(
            @PathVariable Long id,
            @RequestParam(value = "json", required = false) String json,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) {
        return legalPageHeadService.update(id, json, photo);
    }

    @PutMapping("/change-active/{id}")
    public ResponseEntity<ApiResponse<?>> changeActive(@PathVariable Long id) {
        return legalPageHeadService.changeActive(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteLegalPageHead(@PathVariable Long id) {
        return legalPageHeadService.deleteById(id);
    }

}
