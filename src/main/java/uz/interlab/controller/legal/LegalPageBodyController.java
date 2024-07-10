package uz.interlab.controller.legal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.interlab.entity.legal.LegalPageBody;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.legal.LegalPageBodyDTO;
import uz.interlab.service.legal.LegalPageBodyService;

import java.util.List;

@RestController
@RequestMapping("/legal-page-body")
@RequiredArgsConstructor
public class LegalPageBodyController {

    private final LegalPageBodyService legalPageBodyService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<LegalPageBody>> createLegalPageBody(
            @RequestBody LegalPageBody legalPageBody
    ) {
        return legalPageBodyService.create(legalPageBody);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<LegalPageBodyDTO>> getById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return legalPageBodyService.findById(id, lang);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<LegalPageBodyDTO>>> getAll(
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return legalPageBodyService.findAll(lang);
    }

    @GetMapping("/get-full-data/{id}")
    public ResponseEntity<ApiResponse<LegalPageBody>> getFullData(@PathVariable Long id) {
        return legalPageBodyService.findById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<LegalPageBody>> updateLegalPageBody(
            @PathVariable Long id,
            @RequestBody LegalPageBody legalPageBody
    ) {
        return legalPageBodyService.update(id, legalPageBody);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteLegalPageBody(@PathVariable Long id){
        return legalPageBodyService.deleteById(id);
    }

    @PutMapping("/change-active/{id}")
    public ResponseEntity<ApiResponse<?>> changeActive(@PathVariable Long id){
        return legalPageBodyService.changeActive(id);
    }

}
