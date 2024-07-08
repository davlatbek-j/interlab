package uz.interlab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.interlab.entity.analysis.Analysis;
import uz.interlab.payload.AnalysisDTO;
import uz.interlab.payload.AnalysisOptionDTO;
import uz.interlab.payload.ApiResponse;
import uz.interlab.service.AnalysisService;

import java.util.List;

@RestController
@RequestMapping("/analysis")
@RequiredArgsConstructor
public class AnalysisController {

    private final AnalysisService analysisService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Analysis>> createAnalysis(
            @RequestBody Analysis analysis
    ) {
        return analysisService.create(analysis);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<AnalysisDTO>> getById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return analysisService.findById(id, lang);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<AnalysisDTO>>> getAll(
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return analysisService.findAll(lang);
    }

    @GetMapping("/get-full-data/{id}")
    public ResponseEntity<ApiResponse<Analysis>> getFullDate(@PathVariable Long id) {
        return analysisService.findById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Analysis>> updateAnalysis(
            @PathVariable Long id,
            @RequestBody Analysis analysis
    ) {
        return analysisService.update(id, analysis);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteAnalysis(@PathVariable Long id) {
        return analysisService.deleteById(id);
    }

    @PutMapping("/change-active/{id}")
    public ResponseEntity<ApiResponse<?>> changeActive(@PathVariable Long id) {
        return analysisService.changeActive(id);
    }

    @PutMapping("/change-popular/{id}")
    public ResponseEntity<ApiResponse<?>> changePopular(@PathVariable Long id) {
        return analysisService.changePopular(id);
    }

    @GetMapping("/get-popular")
    public ResponseEntity<ApiResponse<List<AnalysisOptionDTO>>> getPopularAnalysisOptions(
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return analysisService.getPopularAnalysisOption(lang);
    }

    @GetMapping("/get/{slug}")
    public ResponseEntity<ApiResponse<AnalysisOptionDTO>> getBySlugAnalysisOptions(
            @PathVariable String slug,
            @RequestHeader(value = "Accept-Language") String lang){
        return analysisService.findBySlug(slug, lang);
    }

}
